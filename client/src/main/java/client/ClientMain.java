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
        while (true) {
            if (authToken == null) {
                PreLogin preUi = new PreLogin(facade, null);
                preUi.runPreLogin();
                authToken = preUi.authToken;
            }
            if (authToken != null) {
                PostLogin postUi = new PostLogin(facade, authToken);
                postUi.runPostLogin();
                authToken = postUi.authToken;
            }
        }
    }
}
