package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO implements GameDAO{

    final private List<GameData> games = new ArrayList<>();
    public void deleteAll() { games.clear();}

    public Integer addGame(GameData data) {
        games.add(data);
        return data.gameID();
    }

    public GameData findGame(Integer gameID){
        return games.get(gameID);
    }

    public void updateGame(GameData gameData){
        games.add(gameData);
    }

    public List<GameData> getGames() {
        return games;
    }
}
