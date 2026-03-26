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
        if (color== ChessGame.TeamColor.WHITE){
            drawWhiteBoard(out, game1);
        } else {drawBlackBoard(out, game1);}
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void drawTopAndBottom(PrintStream out, ChessGame game) {
        String headerText = "";
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
        out.print(" h  g  f  e  d  c  b  a ");
        out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
    }

    private static void drawBlackBoard(PrintStream out, ChessGame game){
        for (int i=1; i <= BOARD_SIZE_IN_SQUARES; i++){
            printEdge(i, out);
            printRow(out, i, game);
            printEdge(i, out);
        }
    }

    private static void drawWhiteBoard(PrintStream out, ChessGame game){
        for (int i=8; i > 0; i--){
            printEdge(i, out);
            printRow(out, i, game);
            printEdge(i, out);
        }
    }

    private static void printRow(PrintStream out, int row, ChessGame game){
        for (int i=1; i <= BOARD_SIZE_IN_SQUARES; i++){
            out.print(BG_LIGHT_BROWN);
            String piece = getPiece(game.board.getPiece(new ChessPosition(row, i)), out);
            out.print(EMPTY);
            out.print(piece);
            out.print(EMPTY);
            out.print(BG_DARK_BROWN);
            String pieceSecond = getPiece(game.board.getPiece(new ChessPosition(row, i)), out);
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
