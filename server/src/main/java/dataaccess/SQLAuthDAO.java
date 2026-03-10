package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import service.exceptions.ResponseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dataaccess.DatabaseCreator.executeUpdate;

public class SQLAuthDAO implements AuthDAO{

    public void addAuthData(AuthData a) throws DataAccessException {
        var statement = "INSERT INTO authTokens (authToken, json) VALUES (?, ?)";
        String json = new Gson().toJson(a);
        try {
            executeUpdate(statement, a.authToken(), json);
        } catch (ResponseException e) {
            throw new DataAccessException("Error: response exception");}
    }

    public AuthData findAuth(String authToken) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, json FROM authTokens WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, AuthData.class);
    }

    public void deleteAll() throws ResponseException {
        var statement = "TRUNCATE authTokens";
        executeUpdate(statement);
    }

    public void deleteAuth(AuthData auth) throws ResponseException {
        var statement = "DELETE FROM authTokens WHERE authToken=?";
        executeUpdate(statement, auth.authToken());
    }

}
