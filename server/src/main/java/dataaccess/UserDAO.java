package dataaccess;
import model.*;
import service.exceptions.ResponseException;

public interface UserDAO {

    void addUser(UserData u) throws DataAccessException;

    UserData findUser(UserData u) throws DataAccessException, ResponseException;

    void deleteAll() throws ResponseException, DataAccessException;
}

