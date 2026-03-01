package dataaccess;

import model.*;

public interface AuthDAO {

    void addAuthData(AuthData a) throws DataAccessException;

    void deleteAll();
}
