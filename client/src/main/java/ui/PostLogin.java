package ui;

import chess.ChessGame;
import chess.requestandresult.CreateRequest;
import chess.requestandresult.JoinRequest;
import chess.requestandresult.ListResult;
import client.ServerFacade;
import model.GameData;
import chess.exceptions.ResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PostLogin {

    private ServerFacade facade;
    public String authToken;
    private List<GameData> gameList = new ArrayList<>();
    private String username;


    public PostLogin(ServerFacade facade, String authToken, String username){
        this.facade = facade;
        this.authToken = authToken;
        this.gameList = null;
        this.username = username;
    }

    public void runPostLogin(){
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input: Please type a number");
            sc.nextLine();
            return;
        }

        int n = sc.nextInt();

        if (n == 1) { help();}
        else if (n == 2) {logout();}
        else if (n == 3) {createGame();}
        else if (n == 4) {listGames();}
        else if (n == 5) {playGame();}
        else if (n == 6) {observe();}
        else {
            System.out.println("Invalid Input");
        }
    }

    private void help(){
        System.out.println("Menu:\n1. Help\n2. Logout\n3. Create Game\n4. List Games\n5. Play Game\n6. Observe Game");
    }

    private void logout(){
        try {
            facade.logout(authToken);
            authToken = null;
            System.out.println("You are logged out! Type 1 for instructions");
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
            System.out.println("Game Created!");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listGames(){
        try {
            ListResult result = facade.listGames(authToken);
            System.out.println("Games:");
            gameList = result.games();
            printGames();
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void playGame(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Game Number:");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input: Please type a number");
                sc.nextLine();
                return;
            }
            Integer gameID = sc.nextInt();
            sc.nextLine();
            System.out.println("Player Color (Type W/B):");
            String colorString = sc.nextLine();
            ChessGame.TeamColor color;
            if (Objects.equals(colorString, "W") || Objects.equals(colorString, "w")) {
                color = ChessGame.TeamColor.WHITE;
            } else if (Objects.equals(colorString, "B") || Objects.equals(colorString, "b")) {
                color = ChessGame.TeamColor.BLACK;
            } else {
                System.out.println("Please rejoin game and type w or b");
                return;
            }
            GameData game = getCorrectGame(gameID);
            if (game==null){
                System.out.println("Game does not exist");
                return;
            }
            if (!checkAlreadyIn(color, game)) {
                JoinRequest request = new JoinRequest(color, gameID, authToken);
                facade.joinGame(request, authToken);
            }
            GamePlay gameUi = new GamePlay(facade, new BoardPrinter(game, color), game, color);
            gameUi.runGamePlay();
        } catch (ResponseException e){
            System.out.println(e.getMessage());
        }
    }

    private boolean checkAlreadyIn(ChessGame.TeamColor color, GameData game){
        if (color== ChessGame.TeamColor.WHITE){
            if (Objects.equals(game.whiteUsername(), username)){
                return true;
            }
        }
        if (color== ChessGame.TeamColor.BLACK){
            if (Objects.equals(game.blackUsername(), username)){
                return true;
            }
        }
        return false;
    }

    private void observe() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Number:");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input: Please type a number");
            sc.nextLine();
            return;
        }
        Integer gameID = sc.nextInt();
        GameData game = getCorrectGame(gameID);
        if (game==null){
            System.out.println("Game does not exist");
            return;
        }
        GamePlay gameUi = new GamePlay(facade, new BoardPrinter(game, ChessGame.TeamColor.WHITE), game, null);
        gameUi.runGamePlay();
    }

    private GameData getCorrectGame(Integer gameID){
        try {
            GameData game = null;
            if (gameList == null) {
                ListResult result = facade.listGames(authToken);
                gameList = result.games();
            }
            for (GameData current : gameList) {
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

    private void printGames(){
        for (GameData current : gameList) {
            System.out.printf("\nGame Number: %d%n", current.gameID());
            System.out.printf("Title: %s%n", current.gameName());
            System.out.printf("White User: %s%n", current.whiteUsername());
            System.out.printf("Black User: %s%n", current.blackUsername());

        }
    }
}
