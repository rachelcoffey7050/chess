package dataaccess;
import model.*;

public interface UserDAO {

    void addUser(UserData u) throws DataAccessException;

    UserData findUser(UserData u) throws DataAccessException;

    void deleteAll();
}

