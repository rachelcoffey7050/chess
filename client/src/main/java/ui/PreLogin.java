package ui;

import client.ServerFacade;
import service.exceptions.ResponseException;
import service.requestandresult.LoginRequest;
import service.requestandresult.LoginResult;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

import java.util.Scanner;

public class PreLogin {

    private final ServerFacade facade;
    public String authToken;
    public Boolean run;

    public PreLogin(ServerFacade facade, String authToken){
        this.facade = facade;
        this.authToken = authToken;
    }

    public void runPreLogin(){
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input: Please type a number");
            sc.nextLine();
            return;
        }

        int n = sc.nextInt();

        if (n == 1) help();
        else if (n == 2) quit();
        else if (n==3) login();
        else if (n == 4) register();
        else {
            System.out.println("Invalid Input");
        }
    }

    private void help(){
        System.out.println("Menu:\n1. Help\n2. Quit\n3. Login\n4. Register");
    }

    private void quit(){
        System.out.println("Goodbye! Come again soon!");
        System.exit(0);
    }

    private void login(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Username:");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            LoginResult result = facade.login(new LoginRequest(username, password), authToken);
            authToken = result.authToken();
            System.out.println("You are logged in! Type 1 for instructions");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void register(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Username:");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            System.out.println("Email:");
            String email = sc.nextLine();
            RegisterResult result = facade.register(new RegisterRequest(username, password, email));
            authToken = result.authToken();
            System.out.println("You are logged in! Type 1 for instructions");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }
}
