package chess.movetypes;

import chess.*;

import java.util.ArrayList;

public class QueenMove extends Moves {

    public QueenMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        this.moves = new ArrayList<>();
        boolean tracker1 = true;
        boolean tracker2 = true;
        boolean tracker3 = true;
        boolean tracker4 = true;
        for (int i = 1; i < 8; i++) {
            ChessPosition p1 = new ChessPosition(position.getRow(), position.getColumn() + i);
            if (tracker1 && inBounds(p1) && (board.getPiece(p1) == null || board.getPiece(p1).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p1, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker1 = false;
                }
            } else {
                tracker1 = false;
            }
            ChessPosition p2 = new ChessPosition(position.getRow() - i, position.getColumn());
            if (tracker2 && inBounds(p2) && (board.getPiece(p2) == null || board.getPiece(p2).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p2, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker2 = false;
                }
            } else {
                tracker2 = false;
            }
            ChessPosition p3 = new ChessPosition(position.getRow(), position.getColumn() - i);
            if (tracker3 && inBounds(p3) && (board.getPiece(p3) == null || board.getPiece(p3).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p3, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker3 = false;
                }
            } else {
                tracker3 = false;
            }
            ChessPosition p4 = new ChessPosition(position.getRow() + i, position.getColumn());
            if (tracker4 && inBounds(p4) && (board.getPiece(p4) == null || board.getPiece(p4).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p4, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker4 = false;
                }
            } else {
                tracker4 = false;
            }
        }
        for (int i = 1; i < 8; i++) {
            ChessPosition p1 = new ChessPosition(position.getRow() + i, position.getColumn() + i);
            if (tracker1 && inBounds(p1) && (board.getPiece(p1) == null || board.getPiece(p1).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p1, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker1 = false;
                }
            } else {
                tracker1 = false;
            }
            ChessPosition p2 = new ChessPosition(position.getRow() - i, position.getColumn() + i);
            if (tracker2 && inBounds(p2) && (board.getPiece(p2) == null || board.getPiece(p2).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p2, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker2 = false;
                }
            } else {
                tracker2 = false;
            }
            ChessPosition p3 = new ChessPosition(position.getRow() - i, position.getColumn() - i);
            if (tracker3 && inBounds(p3) && (board.getPiece(p3) == null || board.getPiece(p3).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p3, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker3 = false;
                }
            } else {
                tracker3 = false;
            }
            ChessPosition p4 = new ChessPosition(position.getRow() + i, position.getColumn() - i);
            if (tracker4 && inBounds(p4) && (board.getPiece(p4) == null || board.getPiece(p4).getTeamColor()!=color)) {
                this.moves.add(new ChessMove(position, p4, null));
                if (board.getPiece(p1).getTeamColor()!=color) {
                    tracker4 = false;
                }
            } else {
                tracker4 = false;
            }
        }
    }
}
