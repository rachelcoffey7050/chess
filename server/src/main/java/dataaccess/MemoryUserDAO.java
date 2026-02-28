package dataaccess;
import dataaccess.UserDAO;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

        final private HashMap<String, UserData> users = new HashMap<>();

        public void addUser(UserData user) {
             UserData u = new UserData(user.username(), user.password(), user.email());

            users.put(user.username(), u);
        }

        public UserData findUser(UserData user) {
            return users.get(user.username());
        }

}
