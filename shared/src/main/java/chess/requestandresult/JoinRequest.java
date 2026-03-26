package chess.requestandresult;

import chess.ChessGame;

public record JoinRequest(ChessGame.TeamColor playerColor, Integer gameID, String authToken) {
}
