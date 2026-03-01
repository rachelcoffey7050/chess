package server;

import io.javalin.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.RegisterService;
import service.exceptions.ResponseException;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        javalin.post("/user", this::registerHandler);
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
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);
        ctx.result(new Gson().toJson(result));
        ctx.status(200);
    }

    private void exceptionHandler(Exception ex, Context ctx){
        ctx.status(500);
        ctx.json(ex.getMessage());
    }

    private void responseExceptionHandler(ResponseException ex, Context ctx){
        ctx.status(ex.toHttpStatusCode());
        ctx.result(ex.toJson());
    }
}
