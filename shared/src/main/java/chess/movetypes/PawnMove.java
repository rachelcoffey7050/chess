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
        ArrayList<ChessPiece.PieceType> types = new ArrayList<>();
        if (newRow==1 || newRow==8) {
            types.add(ChessPiece.PieceType.QUEEN);
            types.add(ChessPiece.PieceType.BISHOP);
            types.add(ChessPiece.PieceType.ROOK);
            types.add(ChessPiece.PieceType.KNIGHT);
        }
        else {types.add(null);}


        for (ChessPiece.PieceType type : types) {
            //forward
            ChessMove forward = new ChessMove(position, new ChessPosition(newRow, position.getColumn()), type);
            if (inBounds(forward.getEndPosition()) && board.getPiece(forward.getEndPosition()) == null) {
                moves.add(forward);
            }

            //attack
            ChessMove attack1 = new ChessMove(position, new ChessPosition(newRow, position.getColumn() - 1), type);
            if (inBounds(attack1.getEndPosition()) && board.getPiece(attack1.getEndPosition()) != null && board.getPiece(attack1.getEndPosition()).getTeamColor() != color) {
                moves.add(attack1);
            }
            ChessMove attack2 = new ChessMove(position, new ChessPosition(newRow, position.getColumn() + 1), type);
            if (inBounds(attack2.getEndPosition()) && board.getPiece(attack2.getEndPosition()) != null && board.getPiece(attack2.getEndPosition()).getTeamColor() != color) {
                moves.add(attack2);
            }
        }
        //first move
        if (position.getRow()==2 && board.getPiece(position).getTeamColor()== ChessGame.TeamColor.WHITE){
            ChessPosition firstMove = new ChessPosition(4, position.getColumn());
            if (board.getPiece(firstMove) == null){
                moves.add(new ChessMove(position, firstMove, null));
            }
        }
        if (position.getRow()==7 && board.getPiece(position).getTeamColor()== ChessGame.TeamColor.BLACK){
            ChessPosition firstMove = new ChessPosition(5, position.getColumn());
            if (board.getPiece(firstMove) == null) {
                moves.add(new ChessMove(position, firstMove, null));
            }
        }
    }
}
