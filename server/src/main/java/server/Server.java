package server;

import chess.ChessGame;
import dataaccess.*;
import io.javalin.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.*;
import service.exceptions.ResponseException;
import service.requestandresult.*;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public Server() {
        this.userDAO = new SQLUserDAO();
        this.authDAO = new SQLAuthDAO();
        this.gameDAO = new SQLGameDAO();
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        try {
            DatabaseCreator.configureDatabase();
        } catch (Exception e){
            System.err.println("Database initialization failed: " + e.getMessage());
            return;
        }

        javalin.post("/user", this::registerHandler);
        javalin.delete("/db", this::deleteHandler);
        javalin.post("/session", this::loginHandler);
        javalin.exception(ResponseException.class, this::responseExceptionHandler);
        javalin.exception(Exception.class, this::exceptionHandler);
        javalin.delete("/session", this::logoutHandler);
        javalin.post("/game", this::createGameHandler);
        javalin.put("/game", this::joinGameHandler);
        javalin.get("/game", this::listHandler);


    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void registerHandler(Context ctx) throws Exception {
        RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);
        RegisterService service = new RegisterService(userDAO, authDAO);
        RegisterResult result = service.register(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void createGameHandler(Context ctx) throws Exception {
        CreateRequest firstRequest = new Gson().fromJson(ctx.body(), CreateRequest.class);
        String gameName = firstRequest.gameName();
        String authToken = ctx.header("Authorization");
        CreateRequest request = new CreateRequest(gameName, authToken);
        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateResult result = service.createGame(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void joinGameHandler(Context ctx) throws Exception {
        JoinRequest firstRequest = new Gson().fromJson(ctx.body(), JoinRequest.class);
        java.lang.Integer gameID = firstRequest.gameID();
        ChessGame.TeamColor color = firstRequest.playerColor();
        String authToken = ctx.header("Authorization");
        JoinRequest request = new JoinRequest(color, gameID, authToken);
        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinResult result = service.joinGame(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void loginHandler(Context ctx) throws Exception {
        LoginRequest request = new Gson().fromJson(ctx.body(), LoginRequest.class);
        LoginService service = new LoginService(userDAO, authDAO);
        LoginResult result = service.login(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void logoutHandler(Context ctx) throws Exception {
        String authToken = ctx.header("Authorization");
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutService service = new LogoutService(authDAO);
        LogoutResult result = service.logout(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void listHandler(Context ctx) throws Exception {
        String authToken = ctx.header("Authorization");
        ListRequest request = new ListRequest(authToken);
        ListService service = new ListService(gameDAO, authDAO);
        ListResult result = service.listGames(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void exceptionHandler(Exception ex, Context ctx){
        ctx.status(500);
        ctx.json(Map.of("message", ex.getMessage()));
    }

    private void deleteHandler(Context ctx) throws Exception {
        DeleteRequest request = new Gson().fromJson(ctx.body(), DeleteRequest.class);
        DeleteService service = new DeleteService(userDAO, authDAO, gameDAO);
        DeleteResult result = service.delete(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void responseExceptionHandler(ResponseException ex, Context ctx){
        ctx.status(ex.toHttpStatusCode());
        ctx.result(ex.toJson());
    }
}
