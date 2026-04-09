package client;

import chess.*;
import ui.PostLogin;
import ui.PreLogin;

public class ClientMain {

    public static void main(String[] args) {
        String authToken = null;
        ServerFacade facade = new ServerFacade("http://localhost:8080");
        String username = null;

        System.out.println("♕ Welcome to 240 Chess! Type 1 for instructions." );
        while (true) {
            if (authToken == null) {
                PreLogin preUi = new PreLogin(facade, null);
                preUi.runPreLogin();
                authToken = preUi.authToken;
                username = preUi.username;
            }
            if (authToken != null) {
                PostLogin postUi = new PostLogin(facade, authToken, username);
                postUi.runPostLogin();
                authToken = postUi.authToken;
            }
        }
    }
}
