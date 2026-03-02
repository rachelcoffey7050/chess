package dataaccess;
import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    public void addAuthData(AuthData auth) {
        AuthData a = new AuthData(auth.username(), auth.authToken());

        authTokens.put(auth.authToken(), a);
    }

    public void deleteAll() { authTokens.clear();}

    public void deleteAuth(AuthData auth) {
        authTokens.remove(auth.authToken());
    }

    public AuthData findAuth(String authToken) {
        return authTokens.get(authToken);
    }

}
