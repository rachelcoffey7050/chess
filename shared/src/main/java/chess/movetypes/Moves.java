package chess.movetypes;

import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class Moves {

    public ArrayList<ChessMove> moves;

    public static boolean inBounds(ChessPosition position) {
        return (0 < position.getRow() && position.getRow() < 9 && position.getColumn() > 0 && position.getColumn() < 9);
    }

    public ArrayList<ChessMove> getMoves(){

        return moves;
    }
}
