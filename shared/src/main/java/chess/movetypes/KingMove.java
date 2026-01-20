package chess.movetypes;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMove {
    public ArrayList<ChessMove> moves;

    public KingMove(ChessBoard board, ChessPosition position) {
        this.moves = new ArrayList<>();
        ArrayList<ChessPosition> positions = new ArrayList<>();
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

    public ArrayList<ChessMove> getMoves(){
        return moves;
    }

    public static boolean inBounds(ChessPosition position) {
        return (0 < position.getRow() && position.getRow() < 9 && position.getColumn() > 0 && position.getColumn() < 9);
    }
}
