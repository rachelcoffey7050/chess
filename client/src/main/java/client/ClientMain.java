package client;

import chess.*;
import server.Server;
import ui.PostLogin;
import ui.PreLogin;

public class ClientMain {

    public static void main(String[] args) {
        String authToken = null;

        Server server = new Server();
        var port = server.run(0);
        ServerFacade facade = new ServerFacade("http://localhost:" + port);

        System.out.println("♕ Welcome to 240 Chess! Type 1 for instructions." );
        PreLogin preUi = new PreLogin(facade);
        PostLogin postUi = new PostLogin(facade, authToken);
        while (true) {
            if (authToken == null) {
                preUi.runPreLogin();
                authToken = preUi.authToken;
            }
            if (authToken != null) {
                postUi.runPostLogin();
                authToken = postUi.authToken;
            }
        }
    }
}
