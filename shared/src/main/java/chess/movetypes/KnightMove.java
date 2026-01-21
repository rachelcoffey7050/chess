package chess.movetypes;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMove extends Moves {

    public KnightMove(ChessBoard board, ChessPosition position){
        this.moves = new ArrayList<>();
        ArrayList<ChessPosition> positions = new ArrayList<>();
        positions.add(new ChessPosition(position.getRow()+2, position.getColumn()+1));
        positions.add(new ChessPosition(position.getRow()+2, position.getColumn()-1));
        positions.add(new ChessPosition(position.getRow()-2, position.getColumn()-1));
        positions.add(new ChessPosition(position.getRow()-2, position.getColumn()+1));
        positions.add(new ChessPosition(position.getRow()+1, position.getColumn()+2));
        positions.add(new ChessPosition(position.getRow()+1, position.getColumn()-2));
        positions.add(new ChessPosition(position.getRow()-1, position.getColumn()-2));
        positions.add(new ChessPosition(position.getRow()-1, position.getColumn()+2));
        for (ChessPosition p: positions) {
            if (inBounds(p) && board.getPiece(position)==null){
                moves.add(new ChessMove(position, p, ChessPiece.PieceType.KING));
            }
        }
    }
}
