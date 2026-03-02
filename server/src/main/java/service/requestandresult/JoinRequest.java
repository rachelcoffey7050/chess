package service.requestandresult;

import chess.ChessGame;

public record JoinRequest(String authToken, ChessGame.TeamColor playerColor, Integer gameID) {
}
