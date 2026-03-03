package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {

    void deleteAll();

    Integer addGame(GameData data) throws DataAccessException;

    GameData findGame(Integer gameID) throws DataAccessException;

    void updateGame(GameData gameData) throws DataAccessException;

    HashMap<Integer, GameData> getGames() throws DataAccessException;

}
