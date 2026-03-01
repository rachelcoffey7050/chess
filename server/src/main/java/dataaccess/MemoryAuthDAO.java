package dataaccess;
import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    public void addAuthData(AuthData auth) {
        AuthData a = new AuthData(auth.username(), auth.authToken());

        authTokens.put(auth.username(), a);
    }

    public void deleteAll() { authTokens.clear();}

}
