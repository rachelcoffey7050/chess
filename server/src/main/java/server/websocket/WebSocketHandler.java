package server.websocket;

import chess.ChessMove;
import chess.ChessPiece;
import com.google.gson.Gson;
import chess.exceptions.ResponseException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

import static websocket.commands.UserGameCommand.CommandType.*;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand action = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (UserGameCommand.CommandType()) {
                case CONNECT -> connect(action.getAuthToken(), action.getGameID(), ctx.session);
                case LEAVE -> leave(action.getAuthToken(), action.getGameID(), ctx.session);
                case MAKE_MOVE -> makeMove(action.getAuthToken(), action.getGameID(), ctx.session);
                case RESIGN -> resign(action.getAuthToken(), action.getGameID(), ctx.session);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(String user, int gameID, Session session) throws IOException {
        connections.add(session);
        var message = String.format("%s is connected", user);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, message);
        connections.broadcast(session, notification);
    }

    private void leave(String user, int gameID, Session session) throws IOException {
        var message = String.format("%s left the game %s", user, gameID);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, notification);
        connections.remove(session);
    }

    private void resign(String user, int gameID, Session session) throws IOException {
        var message = String.format("%s forfeited the game %s", user, gameID);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, notification);
    }

    public void makeMove(String user, int gameID, Session session) throws ResponseException {
        try {
            var message = String.format("%s made a move in game %s", user, gameID);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(session, notification);
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }
}
