package dataaccess;

import model.*;

public interface AuthDAO {

    void addAuthData(AuthData a) throws DataAccessException;

    void deleteAll();

    void deleteAuth(AuthData a) throws DataAccessException;

    AuthData findAuth(String authToken) throws DataAccessException;
}
