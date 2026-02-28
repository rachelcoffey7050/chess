package dataaccess;

import model.*;

public interface AuthDAO {

    void addAuthData(AuthData u) throws DataAccessException;
}
