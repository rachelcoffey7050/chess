package dataaccess;

import model.GameData;

public interface GameDAO {

    void deleteAll();

    Integer addGame(GameData data) throws DataAccessException;

    GameData findGame(Integer gameID) throws DataAccessException;

    void updateGame(GameData gameData) throws DataAccessException;

}
