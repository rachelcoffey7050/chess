package server.websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import chess.exceptions.ResponseException;
import dataaccess.GameDAO;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final GameDAO gameDAO;


    public WebSocketHandler(GameDAO gameDAO){
        this.gameDAO = gameDAO;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            System.out.println("Websocket message sent");
            UserGameCommand action = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (action.getCommandType()) {
                case CONNECT -> connect(action.getAuthToken(), action.getGameID(), ctx.session);
                case LEAVE -> leave(action.getAuthToken(), action.getGameID(), ctx.session);
                case MAKE_MOVE -> makeMove(action.getAuthToken(), action.getGameID(), action.getMove(), ctx.session);
                case RESIGN -> resign(action.getAuthToken(), action.getGameID(), ctx.session);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ResponseException e) {
            System.out.println("Invalid Move");
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(String user, int gameID, Session session) throws IOException {
        try {
            GameData game = gameDAO.findGame(gameID);
            connections.add(gameID, session);
            var loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, game);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, "user joined");
            connections.broadcastToOther(gameID, session, notification);
            connections.broadcast(gameID, session, loadGame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void leave(String user, int gameID, Session session) throws IOException {
        var message = String.format("%s left the game %s", user, gameID);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameID, session, notification);
        connections.remove(session, gameID);
    }

    private void resign(String user, int gameID, Session session) throws IOException {
        var message = String.format("%s forfeited the game %s", user, gameID);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameID, session, notification);
    }

    public void makeMove(String user, int gameID, ChessMove move, Session session) throws ResponseException {
        try {
            GameData game = gameDAO.findGame(gameID);
            game.game().makeMove(move);
            gameDAO.updateGame(game);
            var message = String.format("%s made a move in game %s", user, gameID);
            System.out.println("Received message: " + message);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, message, game);
            connections.broadcast(gameID, session, notification);
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }
}
