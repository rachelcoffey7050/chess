package chess;

import chess.movetypes.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    public ChessGame.TeamColor color;
    public ChessPiece.PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves;
        if (this.type == PieceType.PAWN) {
          moves = PawnMove(board, myPosition);
        } else if (this.type == PieceType.ROOK) {
            RookMove rm = new RookMove(board, myPosition);
            moves = rm.getMoves();
        } else if (this.type == PieceType.KNIGHT) {
            KnightMove km = new KnightMove(board, myPosition);
            moves = km.getMoves();
        } else if (this.type == PieceType.BISHOP) {
            BishopMove bm = new BishopMove(board, myPosition);
            moves = bm.getMoves();
        } else if (this.type == PieceType.KING) {
            KingMove km = new KingMove(board, myPosition);
            moves = km.getMoves();
        } else {
            QueenMove qm = new QueenMove(board, myPosition);
            moves = qm.getMoves();
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }
}
