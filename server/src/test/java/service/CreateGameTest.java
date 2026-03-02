package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.exceptions.AlreadyTakenException;
import service.requestandresult.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTest {

    @Test
    void createSuccess() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        LoginResult resultL = serviceL.login(requestL);


        CreateGameService service = new CreateGameService(new MemoryGameDAO(), authDAO);
        CreateRequest request = new CreateRequest("firstGame", resultL.authToken());
        CreateResult result = service.createGame(request);

        assertNotNull(result.gameID());

    }

    @Test
    void registerDuplicateUser() throws Exception {

        RegisterService service = new RegisterService(new MemoryUserDAO(), new MemoryAuthDAO());

        RegisterRequest request = new RegisterRequest("rachel", "pw", "email");
        RegisterResult result1 = service.register(request);

        assertThrows(AlreadyTakenException.class, () -> service.register(request));
    }

}
