package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import chess.exceptions.BadRequestException;
import chess.exceptions.ResponseException;
import chess.exceptions.UnauthorizedException;
import chess.requestandresult.CreateRequest;
import chess.requestandresult.CreateResult;

public class CreateGameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public CreateGameService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public CreateResult createGame(CreateRequest request)
            throws DataAccessException, ResponseException {
        String authToken = request.authToken();
        String gameName = request.gameName();
        if (authToken==null || gameName==null){
            throw new BadRequestException("Error: bad request");
        }
        AuthData authData = authDAO.findAuth(authToken);
        if (authData == null){
            throw new UnauthorizedException("Error: unauthorized access");
        }

        GameData newGame = new GameData(450, null, null, gameName, new ChessGame());
        Integer gameID = gameDAO.addGame(newGame);
        return new CreateResult(gameID);
    }

}
