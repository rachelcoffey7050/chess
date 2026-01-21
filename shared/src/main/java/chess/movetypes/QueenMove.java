package chess.movetypes;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMove extends Moves {

    public QueenMove(ChessBoard board, ChessPosition position) {
        this.moves = new ArrayList<>();
        boolean tracker1 = true;
        boolean tracker2 = true;
        boolean tracker3 = true;
        boolean tracker4 = true;
        for (int i = 1; i < 8; i++) {
            ChessPosition p1 = new ChessPosition(position.getRow(), position.getColumn() + i);
            if (tracker1 && inBounds(p1) && board.getPiece(p1) == null) {
                this.moves.add(new ChessMove(position, p1, ChessPiece.PieceType.BISHOP));
            } else {
                tracker1 = false;
            }
            ChessPosition p2 = new ChessPosition(position.getRow() - i, position.getColumn());
            if (tracker2 && inBounds(p2) && board.getPiece(p2) == null) {
                this.moves.add(new ChessMove(position, p2, ChessPiece.PieceType.BISHOP));
            } else {
                tracker2 = false;
            }
            ChessPosition p3 = new ChessPosition(position.getRow(), position.getColumn() - i);
            if (tracker3 && inBounds(p3) && board.getPiece(p3) == null) {
                this.moves.add(new ChessMove(position, p3, ChessPiece.PieceType.BISHOP));
            } else {
                tracker3 = false;
            }
            ChessPosition p4 = new ChessPosition(position.getRow() + i, position.getColumn());
            if (tracker4 && inBounds(p4) && board.getPiece(p4) == null) {
                this.moves.add(new ChessMove(position, p4, ChessPiece.PieceType.BISHOP));
            } else {
                tracker4 = false;
            }
        }
        for (int i = 1; i < 8; i++) {
            ChessPosition p1 = new ChessPosition(position.getRow() + i, position.getColumn() + i);
            if (tracker1 && inBounds(p1) && board.getPiece(p1) == null) {
                this.moves.add(new ChessMove(position, p1, ChessPiece.PieceType.BISHOP));
            } else {
                tracker1 = false;
            }
            ChessPosition p2 = new ChessPosition(position.getRow() - i, position.getColumn() + i);
            if (tracker2 && inBounds(p2) && board.getPiece(p2) == null) {
                this.moves.add(new ChessMove(position, p2, ChessPiece.PieceType.BISHOP));
            } else {
                tracker2 = false;
            }
            ChessPosition p3 = new ChessPosition(position.getRow() - i, position.getColumn() - i);
            if (tracker3 && inBounds(p3) && board.getPiece(p3) == null) {
                this.moves.add(new ChessMove(position, p3, ChessPiece.PieceType.BISHOP));
            } else {
                tracker3 = false;
            }
            ChessPosition p4 = new ChessPosition(position.getRow() + i, position.getColumn() - i);
            if (tracker4 && inBounds(p4) && board.getPiece(p4) == null) {
                this.moves.add(new ChessMove(position, p4, ChessPiece.PieceType.BISHOP));
            } else {
                tracker4 = false;
            }
        }
    }
}
