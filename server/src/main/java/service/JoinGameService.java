package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;
import service.requestandresult.JoinRequest;
import service.requestandresult.JoinResult;

public class JoinGameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public JoinGameService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public JoinResult joinGame(JoinRequest request)
            throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {

        AuthData authData = authDAO.findAuth(request.authToken());
        if (authData==null){
            throw new UnauthorizedException("Error: unauthorized");
        }

        GameData gameData = gameDAO.findGame(request.gameID());
        if (gameData==null) {
            throw new BadRequestException("Error: bad request, game does not exist");
        }
        ChessGame.TeamColor color = request.playerColor();
        if (color== ChessGame.TeamColor.BLACK){
            if (gameData.blackUsername()!=null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData newData = new GameData(gameData.gameID(), gameData.whiteUsername(),
                                authData.username(), gameData.gameName(), gameData.game());
            gameDAO.updateGame(newData);
        }
        else if (color== ChessGame.TeamColor.WHITE){
            if (gameData.whiteUsername()!=null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData newData2 = new GameData(gameData.gameID(), authData.username(),
                    gameData.blackUsername(), gameData.gameName(), gameData.game());
            gameDAO.updateGame(newData2);
        }
        return new JoinResult();
    }

}
