package chess.movetypes;

import chess.*;

import java.util.ArrayList;

public class KnightMove extends Moves {

    public KnightMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color){
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
            if (inBounds(p) && (board.getPiece(p)==null || board.getPiece(p).getTeamColor()!=color)){
                moves.add(new ChessMove(position, p, null));
            }
        }
    }
}
