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
            if (board.getPiece(forward.getEndPosition()) == null && inBounds(forward.getEndPosition())) {
                moves.add(forward);
            }

            //attack
            ChessMove attack1 = new ChessMove(position, new ChessPosition(newRow, position.getColumn() - 1), type);
            if (board.getPiece(attack1.getEndPosition()) != null && board.getPiece(attack1.getEndPosition()).getTeamColor() != color && inBounds(attack1.getEndPosition())) {
                moves.add(attack1);
            }
            ChessMove attack2 = new ChessMove(position, new ChessPosition(newRow, position.getColumn() + 1), type);
            if (board.getPiece(attack2.getEndPosition()) != null && board.getPiece(attack2.getEndPosition()).getTeamColor() != color && inBounds(attack2.getEndPosition())) {
                moves.add(attack2);
            }
        }
        //first move
        if (position.getRow()==2 && board.getPiece(position).getTeamColor()== ChessGame.TeamColor.WHITE){
            moves.add(new ChessMove(position, new ChessPosition(4, position.getColumn()), null));
        }
        if (position.getRow()==7 && board.getPiece(position).getTeamColor()== ChessGame.TeamColor.BLACK){
            moves.add(new ChessMove(position, new ChessPosition(5, position.getColumn()), null));
        }

    }
}
