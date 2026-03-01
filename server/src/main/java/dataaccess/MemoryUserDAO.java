package dataaccess;
import dataaccess.UserDAO;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

        final private HashMap<String, UserData> users = new HashMap<>();

        public void addUser(UserData user) {
            users.put(user.username(), user);
        }

        public UserData findUser(UserData user) {
            return users.get(user.username());
        }

        public void deleteAll() { users.clear();}
}
