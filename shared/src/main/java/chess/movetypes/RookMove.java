package chess.movetypes;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class RookMove extends Moves{

    public RookMove(ChessBoard board, ChessPosition current, ChessGame.TeamColor color){
        this.moves = new ArrayList<>();
        getRookMoves(this.moves, board, current, color);
    }

    protected static void getRookMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition current, ChessGame.TeamColor color){
        boolean tracker1 = true;
        boolean tracker2 = true;
        boolean tracker3 = true;
        boolean tracker4 = true;
        for (int i=1; i<9; i++) {
            if (tracker1) {
                ChessPosition position1 = new ChessPosition(current.getRow() + i, current.getColumn());
                if (inBounds(position1) && (board.getPiece(position1) == null || board.getPiece(position1).getTeamColor() != color)) {
                    moves.add(new ChessMove(current, position1, null));
                    if (board.getPiece(position1) != null){
                        tracker1 = false;
                    }
                } else {tracker1 = false;}
            }
            if (tracker2) {
                ChessPosition position2 = new ChessPosition(current.getRow() - i, current.getColumn());
                if (inBounds(position2) && (board.getPiece(position2) == null || board.getPiece(position2).getTeamColor() != color)) {
                    moves.add(new ChessMove(current, position2, null));
                    if (board.getPiece(position2) != null){
                        tracker2 = false;
                    }
                } else {tracker2 = false;}
            }
            if (tracker3) {
                ChessPosition position3 = new ChessPosition(current.getRow(), current.getColumn()+i);
                if (inBounds(position3) && (board.getPiece(position3) == null || board.getPiece(position3).getTeamColor() != color)) {
                    moves.add(new ChessMove(current, position3, null));
                    if (board.getPiece(position3) != null){
                        tracker3 = false;
                    }
                } else {tracker3 = false;}
            }
            if (tracker4) {
                ChessPosition position4 = new ChessPosition(current.getRow(), current.getColumn()-i);
                if (inBounds(position4) && (board.getPiece(position4) == null || board.getPiece(position4).getTeamColor() != color)) {
                    moves.add(new ChessMove(current, position4, null));
                    if (board.getPiece(position4) != null){
                        tracker4 = false;
                    }
                } else {tracker4 = false;}
            }
        }
    }

}
