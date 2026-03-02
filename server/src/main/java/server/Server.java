package server;

import dataaccess.*;
import io.javalin.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.DeleteService;
import service.LoginService;
import service.LogoutService;
import service.RegisterService;
import service.exceptions.ResponseException;
import service.requestandresult.*;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public Server() {
        this.userDAO = new MemoryUserDAO();
        this.authDAO = new MemoryAuthDAO();
        this.gameDAO = new MemoryGameDAO();
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        javalin.post("/user", this::registerHandler);
        javalin.delete("/db", this::deleteHandler);
        javalin.post("/session", this::loginHandler);
        javalin.exception(ResponseException.class, this::responseExceptionHandler);
        javalin.exception(Exception.class, this::exceptionHandler);
        javalin.delete("/session", this::logoutHandler);


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
