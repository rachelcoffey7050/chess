package dataaccess;

import dataaccess.DatabaseCreator.*;
import com.google.gson.Gson;
import model.UserData;
import service.exceptions.ResponseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {




    public void addUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO users (username, json) VALUES (?, ?)";
        String json = new Gson().toJson(user);
        try {
             DatabaseCreator.executeUpdate(statement, user.username(), json);
        } catch (ResponseException e) {
            throw new DataAccessException("Error: response exception");}
    }

    public UserData findUser(UserData user) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, json FROM users WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Error: Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, UserData.class);
    }

    public void deleteAll() throws ResponseException {
        var statement = "TRUNCATE users";
        DatabaseCreator.executeUpdate(statement);
    }

}
