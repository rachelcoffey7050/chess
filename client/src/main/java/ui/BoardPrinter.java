package ui;

import chess.ChessGame;
import model.GameData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class BoardPrinter {

    private GameData game;
    private ChessGame.TeamColor color;
    private static final int BOARD_SIZE_IN_SQUARES = 10;
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

        drawTopAndBottom(out);
    }

    private static void drawTopAndBottom(PrintStream out) {
        String headerText = "";
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
        out.print(" h  g  f  e  d  c  b  a ");
        out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
    }
}
