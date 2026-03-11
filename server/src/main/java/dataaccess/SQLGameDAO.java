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
        var statement = "INSERT INTO games (id, json) VALUES (?, ?)";
        String json = new Gson().toJson(game);
        Integer id = executeUpdate(statement, game.gameID(), json);
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
        var statement = "DELETE FROM games WHERE id=?";
        executeUpdate(statement, gameData.gameID());
        var statement2 = "INSERT INTO games (id, json) VALUES (?, ?)";
        String json = new Gson().toJson(gameData);
        executeUpdate(statement2, gameData.gameID(), json);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, GameData.class);
    }

    public void deleteAll() throws ResponseException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }
}
