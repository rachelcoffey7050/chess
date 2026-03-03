package dataaccess;

import model.GameData;

import java.util.HashMap;


public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    public void deleteAll() { games.clear();}
    private Integer counter = 1;

    public Integer addGame(GameData data) {
        GameData newData = new GameData(counter, data.whiteUsername(), data.blackUsername(), data.gameName(), data.game());
        games.put(counter, newData);
        counter+=1;
        return newData.gameID();
    }

    public GameData findGame(Integer gameID){
        return games.get(gameID);
    }

    public void updateGame(GameData gameData){
        games.put(gameData.gameID(), gameData);
    }

    public HashMap<Integer, GameData> getGames() {
        return games;
    }
}
