package dataaccess;

import com.google.gson.Gson;
import model.GameData;
import service.exceptions.ResponseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static dataaccess.DatabaseCreator.executeUpdate;

public class SQLGameDAO implements GameDAO{

    public Integer addGame(GameData game) throws DataAccessException, ResponseException {
        var statement = "INSERT INTO games (json) VALUES (?)";
        String json = new Gson().toJson(game);
        Integer id = executeUpdate(statement, json); //insert placeholder
        GameData updated = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        updateGame(updated);
        return id;
    }

    public HashMap<Integer, GameData> getGames() throws ResponseException {
        var result = new HashMap<Integer, GameData>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM games";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        GameData eachGame = readGame(rs);
                        result.put(eachGame.gameID(), eachGame);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Error: Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public GameData findGame(Integer gameID) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM games WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Error: Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public void updateGame(GameData gameData) throws ResponseException {
        var statement = "UPDATE games SET json=? WHERE id=?";
        String json = new Gson().toJson(gameData);
        executeUpdate(statement, json, gameData.gameID());
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var json = rs.getString("json");
        GameData newGame = new Gson().fromJson(json, GameData.class);
        // pet shop updated the id but to do that I'd have to change the way it is implemented
        return newGame;
    }

    public void deleteAll() throws ResponseException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }
}
