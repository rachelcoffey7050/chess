package ui;

import chess.*;
import chess.exceptions.ResponseException;
import client.ServerFacade;
import model.GameData;
import websocket.WebSocketFacade;
import websocket.WebSocketFacade.*;

import java.util.Scanner;

public class GamePlay {

    private final ServerFacade facade;
    private BoardPrinter printer;
    private ChessGame game;
    private final ChessGame.TeamColor color;
    private final String authToken;
    private final int gameID;

    public GamePlay(ServerFacade facade, BoardPrinter printer, GameData game,
                    ChessGame.TeamColor color, String authToken){
        this.facade = facade;
        this.printer = printer;
        this.game = game.game();
        this.color = color;
        this.authToken = authToken;
        this.gameID = game.gameID();
    }

    public void runGamePlay(){

        Scanner sc = new Scanner(System.in);
        WebSocketFacade webSocketFacade = new WebSocketFacade(facade.serverUrl, notification -> {
            switch (notification.getServerMessageType()) {
                case LOAD_GAME -> { try {
                    GameData updated = notification.getGame();
                    this.game = updated.game();
                    this.printer = new BoardPrinter(updated, color);
                    printer.printBoard();
                } catch (Exception e) {
                    System.out.println("Error updating game: " + e.getMessage());
                } }
                case NOTIFICATION -> System.out.println(notification.getMessage());
                case ERROR -> System.out.println(notification.getMessage());
            }});
            webSocketFacade.connect(authToken,gameID);


        boolean playing = true;

        while (playing) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input: Please type a number");
                sc.nextLine();
                return;
            }

            int n = sc.nextInt();

            if (n == 1) {
                help();
            } else if (n == 2) {
                redrawChessBoard();
            } else if (n == 3) {
                leave(webSocketFacade);
                playing = false;
            } else if (n == 4) {
                makeMove(webSocketFacade);
            } else if (n == 5) {
                resign(webSocketFacade);
            } else if (n == 6) {
                highlight();
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private void help(){
        System.out.println("Menu:\n1. Help\n2. Redraw Chess Board\n3. Leave\n4. Make Move\n5. Resign\n6. Highlight Legal Moves ");
    }

    private void leave(WebSocketFacade websocket){
        websocket.leave(authToken, gameID);
        System.out.println("Leaving Chess Game");
    }

    private void redrawChessBoard(){
        printer.printBoard();
    }

    private void makeMove(WebSocketFacade websocket){
        if (color == null){
            System.out.println("You are an observer, you cannot make moves");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Row of Chess Piece to move (1-8):");
        int row = getInt(sc);
        if (row == 0){return;}
        System.out.println("Column of Chess Piece to move (1-8):");
        int col = getInt(sc);
        if (col == 0){return;}
        ChessPosition initial = new ChessPosition(row, col);
        ChessPiece movePiece = game.board.getPiece(initial);
        if (movePiece == null){
            System.out.println("There is not a Piece at that location");
            return;
        }

        System.out.println("Row of Destination (1-8):");
        int rowDes = getInt(sc);
        if (rowDes == 0){return;}
        System.out.println("Column of Destination (1-8):");
        int colDes = getInt(sc);
        if (colDes == 0){return;}

        ChessPiece.PieceType promotion = null;
        if ((movePiece.getPieceType()==ChessPiece.PieceType.PAWN && rowDes==8 && color== ChessGame.TeamColor.WHITE)
                || (movePiece.getPieceType()==ChessPiece.PieceType.PAWN && rowDes==1 &&
                color== ChessGame.TeamColor.BLACK)){
            System.out.println("Promotion Piece:");
            promotion = getPromotionPiece(sc);
        }
        ChessMove newMove = new ChessMove(initial, new ChessPosition(rowDes, colDes), promotion);
        websocket.makeMove(authToken, gameID, newMove);
    }

    private int getInt(Scanner sc){
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input: Please type a number");
            sc.nextLine();
            return 0;
        }
        int int1 = sc.nextInt();
        if (int1>8 || int1<1){
            System.out.println("Invalid input: Number must be between 1 and 8");
            sc.nextLine();
            return 0;
        }
        return int1;
    }

    private ChessPiece.PieceType getPromotionPiece(Scanner sc){
        String piece = sc.nextLine().toLowerCase();
        if (piece.equals("pawn")){
            return ChessPiece.PieceType.PAWN;
        } else if (piece.equals("knight")) {
            return ChessPiece.PieceType.KNIGHT;
        } else if (piece.equals("rook")) {
            return ChessPiece.PieceType.ROOK;
        } else if (piece.equals("bishop")) {
            return ChessPiece.PieceType.KNIGHT;
        }else if (piece.equals("queen")) {
            return ChessPiece.PieceType.QUEEN;
        } else if (piece.equals("king")) {
            return ChessPiece.PieceType.KING;
        }
        return null;
    }

     private void resign(WebSocketFacade websocket){
        websocket.resign(authToken, gameID);
        System.out.println("Game Over: Forfeited");
         // websocket ends game?
     }

     private void highlight(){
         Scanner sc = new Scanner(System.in);
         System.out.println("Row of Chess Piece to check (1-8):");
         int row = getInt(sc);
         if (row == 0){return;}
         System.out.println("Column of Chess Piece to check (1-8):");
         int col = getInt(sc);
         if (col == 0){return;}
         printer.highlightPrint(new ChessPosition(row, col));
     }
}
