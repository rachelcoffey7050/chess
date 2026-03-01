package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> games = new HashMap<>();
    public void deleteAll() { games.clear();}
}
