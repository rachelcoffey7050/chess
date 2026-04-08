package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import client.ServerFacade;
import model.GameData;

import java.util.Scanner;

public class GamePlay {

    private final ServerFacade facade;
    private final BoardPrinter printer;
    private ChessGame game;
    private ChessGame.TeamColor color;

    public GamePlay(ServerFacade facade, BoardPrinter printer, GameData game, ChessGame.TeamColor color){
        this.facade = facade;
        this.printer = printer;
        this.game = game.game();
        this.color = color;
    }

    public void runGamePlay(){
        Scanner sc = new Scanner(System.in);
        boolean playing = true;
        printer.printBoard();

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
                leave();
                playing = false;
            } else if (n == 4) {
                makeMove();
            } else if (n == 5) {
                resign();
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

    private void leave(){
        System.out.println("Leaving Chess Game");
    }

    private void redrawChessBoard(){
        printer.printBoard();
    }

    private void makeMove(){
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

        try {
            game.makeMove(new ChessMove(initial, new ChessPosition(rowDes, colDes), promotion));
        } catch (Exception e) {
            System.out.println("Error: Invalid Move");
            return;
        }

        // make move with websocket?
        printer.printBoard();
    }

    private int getInt(Scanner sc){
        if (!sc.hasNextInt() || sc.nextInt()>8 || sc.nextInt()<1) {
            System.out.println("Invalid input: Please type a number 1-8");
            sc.nextLine();
            return 0;
        }
        return sc.nextInt();
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

     private void resign(){
         System.out.println("Game Over: Forfeited");
         // websocket ends game?
     }

     private void highlight(){
        //get list of legal moves from chess game and print them out
     }
}
