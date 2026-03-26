package ui;

import chess.ChessGame;
import client.ServerFacade;
import model.GameData;
import service.exceptions.ResponseException;
import service.requestandresult.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PostLogin {

    private ServerFacade facade;
    private String authToken;
    private List<GameData> gameList = new ArrayList<>();


    public void PreLogin(ServerFacade facade, String authToken){
        this.facade = facade;
        this.authToken = authToken;
        this.gameList = null;
    }

    public void runPostLogin(){
        System.out.println("You are logged in! Type 1 to get started");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        if (n == 1) help();
        else if (n == 2) logout();
        else if (n == 3) createGame();
        else if (n == 4) listGames();
        else if (n == 5) playGame();
        else if (n == 6) observe();
        else {
            System.out.println("Invalid Input");
        }
    }

    private void help(){
        System.out.println("1. Help\n2. Logout\n3. Create Game\n4. List Games\n5. Play Game\n6. Observe Game");
    }

    private void logout(){
        try {
            facade.logout(authToken);
            authToken = null;
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createGame(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Game Name:");
            String gameName = sc.nextLine();
            CreateRequest request = new CreateRequest(gameName, authToken);
            facade.createGame(request, authToken);
            ListResult result = facade.listGames(authToken);
            gameList = result.games();
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listGames(){
        try {
            ListResult result = facade.listGames(authToken);
            System.out.println("Games:");
            System.out.println(result.games());
            gameList = result.games();
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void playGame(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Game Number:");
            Integer gameID = sc.nextInt();
            System.out.println("Player Color (Type W/B):");
            String colorString = sc.nextLine();
            ChessGame.TeamColor color;
            if (Objects.equals(colorString, "W") || Objects.equals(colorString, "w")) {
                color = ChessGame.TeamColor.WHITE;
            } else {
                color = ChessGame.TeamColor.BLACK;
            }
            JoinRequest request = new JoinRequest(color, gameID, authToken);
            facade.joinGame(request, authToken);
            GameData game = getCorrectGame(gameID);
            BoardPrinter printer = new BoardPrinter(game, color);
            printer.printBoard();
        } catch (ResponseException e){
            System.out.println(e.getMessage());
        }
    }
    private void observe() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Number:");
        Integer gameID = sc.nextInt();
        GameData game = getCorrectGame(gameID);
        BoardPrinter printer = new BoardPrinter(game, null);
        printer.printBoard();
    }

    private GameData getCorrectGame(Integer gameID){
        try {
            GameData game = null;
            if (gameList == null) {
                ListResult result = facade.listGames(authToken);
                gameList = result.games();
            }
            for (int i = 0; i < gameList.size(); i++){
                GameData current = gameList.get(i);
                if (current.gameID() == gameID) {
                    game = current;
                }
            }
            return game;
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
