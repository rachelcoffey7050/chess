package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    public void deleteAll() { games.clear();}

    public Integer addGame(GameData data) {
        games.put(data.gameID(), data);
        return data.gameID();
    }

    public GameData findGame(Integer gameID){
        return games.get(gameID);
    }

    public void updateGame(GameData gameData){
        games.put(gameData.gameID(), gameData);
    }
}
