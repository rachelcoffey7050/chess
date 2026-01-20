package chess.movetypes;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingMove {
    public Collection<ChessMove> moves;

    public   KingMove (ChessBoard board, ChessPosition position) {
        this.moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> positions = new ArrayList<ChessPosition>();
        positions.add(new ChessPosition(position.getRow()-1, position.getColumn()));
        positions.add(new ChessPosition(position.getRow()+1, position.getColumn()));
        positions.add(new ChessPosition(position.getRow(), position.getColumn()-1));
        positions.add(new ChessPosition(position.getRow(), position.getColumn()+1));
        for (ChessPosition p: positions) {
            if (inBounds(p) && board.getPiece(position)==null){
                moves.add(new ChessMove(position, p, ChessPiece.PieceType.KING));
            }
        }


    }

    public static boolean inBounds(ChessPosition position) {
        if (0 < position.getRow() && position.getRow() < 9 && position.getColumn() > 0 && position.getColumn() < 9) {
            return true;
        }
        else {
            return false;
        }
    }
}
