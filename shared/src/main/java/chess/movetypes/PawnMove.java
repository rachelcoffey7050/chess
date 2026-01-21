package chess.movetypes;

import chess.*;

import java.util.ArrayList;

public class PawnMove extends Moves{

    public PawnMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        this.moves = new ArrayList<>();

        int newRow;
        if (color == ChessGame.TeamColor.WHITE) {
            newRow = position.getRow()+1;
        } else {newRow = position.getRow()-1;}

        //promotion
        ChessPiece.PieceType type;
        if (newRow==1 || newRow==8) {
            type = ChessPiece.PieceType.QUEEN;
        } else {type = ChessPiece.PieceType.PAWN;}

        //forward
        ChessMove forward = new ChessMove(position, new ChessPosition(newRow, position.getColumn()), type);
        if (board.getPiece(forward.getEndPosition())==null && inBounds(forward.getEndPosition())){
            moves.add(forward);
        }

        //attack
        ChessMove attack1 = new ChessMove(position, new ChessPosition(newRow, position.getColumn()-1), type);
        if (board.getPiece(forward.getEndPosition())!=null && board.getPiece(forward.getEndPosition()).getTeamColor()!=color && inBounds(forward.getEndPosition())){
            moves.add(attack1);
        }
        ChessMove attack2 = new ChessMove(position, new ChessPosition(newRow, position.getColumn()+1), type);
        if (board.getPiece(forward.getEndPosition())!=null && board.getPiece(forward.getEndPosition()).getTeamColor()!=color && inBounds(forward.getEndPosition())){
            moves.add(attack2);
        }
    }
}
