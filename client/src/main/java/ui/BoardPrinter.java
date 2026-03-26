package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessPiece.PieceType.*;
import static ui.EscapeSequences.*;

public class BoardPrinter {

    private final GameData game;
    private final ChessGame.TeamColor color;
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;

    public BoardPrinter(GameData game, ChessGame.TeamColor color){
        this.game = game;
        this.color = color;
    }

    public void printBoard(){
        if (game == null){
            System.out.println("Could not print board: game was null");
        }
        assert game != null;
        ChessGame game1 = game.game();

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        drawTopAndBottom(out, game1);
        if (color== ChessGame.TeamColor.BLACK){
            drawBlackBoard(out, game1);
        } else {drawWhiteBoard(out, game1);}
        drawTopAndBottom(out, game1);
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void drawTopAndBottom(PrintStream out, ChessGame game) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(EMPTY + " " + EMPTY);
        out.print(EMPTY + "h" + EMPTY  + EMPTY + "g" + EMPTY + EMPTY + "f" + EMPTY + EMPTY + "e" + EMPTY + EMPTY
                    + "d" + EMPTY + EMPTY + "c" + EMPTY + EMPTY + "b" + EMPTY + EMPTY + "a" + EMPTY);
        out.print(EMPTY + " " + EMPTY);
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private static void drawBlackBoard(PrintStream out, ChessGame game){
        for (int i=1; i <= BOARD_SIZE_IN_SQUARES; i++){
            String BG_COLOR_ONE;
            String BG_COLOR_TWO;
            if (i%2==0){
                BG_COLOR_ONE = BG_DARK_BROWN;
                BG_COLOR_TWO = BG_LIGHT_BROWN;
            } else {
                BG_COLOR_ONE = BG_LIGHT_BROWN;
                BG_COLOR_TWO = BG_DARK_BROWN;
            }
            printBlankEdge(out);
            printBlankRow(out, BG_COLOR_ONE, BG_COLOR_TWO);
            printBlankEdge(out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
            printEdge(i, out);
            printRow(out, i, game, BG_COLOR_ONE, BG_COLOR_TWO);
            printEdge(i, out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
            printBlankEdge(out);
            printBlankRow(out, BG_COLOR_ONE, BG_COLOR_TWO);
            printBlankEdge(out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
        }
    }

    private static void printBlankEdge(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(EMPTY);
        out.print(" ");
        out.print(EMPTY);
    }

    private static void printBlankRow(PrintStream out, String color1, String color2){
        for (int i=1; i <= BOARD_SIZE_IN_SQUARES; i+=2){
            out.print(color1);
            out.print(EMPTY);
            out.print(" ");
            out.print(EMPTY);
            out.print(color2);
            out.print(EMPTY);
            out.print(" ");
            out.print(EMPTY);
        }
    }

    private static void drawWhiteBoard(PrintStream out, ChessGame game){
        for (int i=8; i > 0; i--){
            String BG_COLOR_1;
            String BG_COLOR_2;
            if (i%2==0){
                BG_COLOR_1 = BG_DARK_BROWN;
                BG_COLOR_2 = BG_LIGHT_BROWN;
            } else {
                BG_COLOR_2 = BG_DARK_BROWN;
                BG_COLOR_1 = BG_LIGHT_BROWN;
            }

            printBlankEdge(out);
            printBlankRow(out, BG_COLOR_1, BG_COLOR_2);
            printBlankEdge(out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
            printEdge(i, out);
            printRow(out, i, game, BG_COLOR_1, BG_COLOR_2);
            printEdge(i, out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
            printBlankEdge(out);
            printBlankRow(out, BG_COLOR_1, BG_COLOR_2);
            printBlankEdge(out);
            out.print(RESET_BG_COLOR);
            out.print("\n");
        }
    }

    private static void printRow(PrintStream out, int row, ChessGame game, String color1, String color2){
        for (int i=1; i <= BOARD_SIZE_IN_SQUARES; i+=2){
            out.print(color1);
            String piece = getPiece(game.board.getPiece(new ChessPosition(row, i)), out);
            out.print(EMPTY);
            out.print(piece);
            out.print(EMPTY);
            out.print(color2);
            String pieceSecond = getPiece(game.board.getPiece(new ChessPosition(row, i+1)), out);
            out.print(EMPTY);
            out.print(pieceSecond);
            out.print(EMPTY);
        }
    }

    private static void printEdge(int i, PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(EMPTY);
        out.print(i);
        out.print(EMPTY);
    }

    private static String getPiece(ChessPiece chessPiece, PrintStream out){
        if (chessPiece==null){
            return " ";
        }
        if (chessPiece.color == ChessGame.TeamColor.WHITE){
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }
        if (chessPiece.type == PAWN){
            return "P";
        } else if (chessPiece.type == ROOK) {
            return "R";
        } else if (chessPiece.type == KNIGHT) {
            return "N";
        } else if (chessPiece.type == BISHOP){
            return "B";
        } else if (chessPiece.type == QUEEN) {
            return "Q";
        } else {
         return "K";
        }
    }
}
