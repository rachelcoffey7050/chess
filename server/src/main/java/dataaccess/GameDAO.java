package dataaccess;

import model.GameData;
import chess.exceptions.ResponseException;

import java.util.HashMap;

public interface GameDAO {

    void deleteAll() throws ResponseException;

    Integer addGame(GameData data) throws DataAccessException, ResponseException;

    GameData findGame(Integer gameID) throws DataAccessException, ResponseException;

    void updateGame(GameData gameData) throws DataAccessException, ResponseException;

    HashMap<Integer, GameData> getGames() throws DataAccessException, ResponseException;

}
