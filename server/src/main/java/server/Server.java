package server;

import dataaccess.*;
import io.javalin.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.DeleteService;
import service.RegisterService;
import service.exceptions.ResponseException;
import service.requestandresult.DeleteRequest;
import service.requestandresult.DeleteResult;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

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
        javalin.exception(ResponseException.class, this::responseExceptionHandler);
        javalin.exception(Exception.class, this::exceptionHandler);


    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    // haha ignore this I forgot about the handlers
    private void registerHandler(Context ctx) throws Exception {
        RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);
        RegisterService service = new RegisterService(userDAO, authDAO);
        RegisterResult result = service.register(request);
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
