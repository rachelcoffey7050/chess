package dataaccess;

import model.GameData;

public interface GameDAO {

    void deleteAll();

    Integer addGame(GameData data) throws DataAccessException;
}
