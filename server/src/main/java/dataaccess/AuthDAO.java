package dataaccess;

import model.*;
import service.exceptions.ResponseException;

public interface AuthDAO {

    void addAuthData(AuthData a) throws DataAccessException;

    void deleteAll() throws DataAccessException, ResponseException;

    void deleteAuth(AuthData a) throws DataAccessException, ResponseException;

    AuthData findAuth(String authToken) throws DataAccessException, ResponseException;
}
