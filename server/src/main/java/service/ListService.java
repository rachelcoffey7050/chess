package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import chess.exceptions.ResponseException;
import chess.exceptions.UnauthorizedException;
import chess.requestandresult.ListRequest;
import chess.requestandresult.ListResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public ListService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public ListResult listGames(ListRequest request) throws DataAccessException, ResponseException {
        AuthData authData = authDAO.findAuth(request.authToken());
        if (authData==null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        HashMap<Integer, GameData> games = gameDAO.getGames();
        List<GameData> listData = new ArrayList<>(games.values());
        return new ListResult(listData);
    }
}
