package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;
import service.requestandresult.CreateRequest;
import service.requestandresult.CreateResult;

public class CreateGameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    Integer counter;

    public CreateGameService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.counter = 999;
    }

    public CreateResult createGame(CreateRequest request)
            throws DataAccessException, UnauthorizedException, BadRequestException {
        String authToken = request.authToken();
        String gameName = request.gameName();
        if (authToken==null || gameName==null){
            throw new BadRequestException("Error: bad request");
        }
        AuthData authData = authDAO.findAuth(authToken);
        if (authData == null){
            throw new UnauthorizedException("Error: unauthorized access");
        }
        counter += 1;
        GameData newGame = new GameData(counter, null, null, gameName, new ChessGame());
        gameDAO.addGame(newGame);
        return new CreateResult(counter);
    }

}
