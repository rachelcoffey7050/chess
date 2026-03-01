package server;

import io.javalin.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}

//try {
//    User user = userService.login(request.username(), request.password());
//    sendResponse(200, user);
//}
//catch (UnauthorizedException e) {
//    sendResponse(401, e.getMessage());
//}
//catch (BadRequestException e) {
//    sendResponse(400, e.getMessage());
//}
//catch (ServiceException e) {
//    sendResponse(500, "Error: " + e.getMessage());
//}
