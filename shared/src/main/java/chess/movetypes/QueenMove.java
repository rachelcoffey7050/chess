package chess.movetypes;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMove extends Moves{

    public QueenMove(ChessBoard board, ChessPosition current, ChessGame.TeamColor color){
        this.moves = new ArrayList<>();
        BishopMove.getBishopMoves(this.moves, board, current, color);
        RookMove.getRookMoves(this.moves, board, current, color);
    }
}
