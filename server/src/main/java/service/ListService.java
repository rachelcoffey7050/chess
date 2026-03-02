package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import service.exceptions.UnauthorizedException;
import service.requestandresult.ListRequest;
import service.requestandresult.ListResult;

import java.util.HashMap;
import java.util.List;

public class ListService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public ListService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListResult listGames(ListRequest request) throws DataAccessException, UnauthorizedException {
        AuthData authData = authDAO.findAuth(request.authToken());
        if (authData==null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        List<GameData> games = gameDAO.getGames();
        return new ListResult(games);
    }
}
