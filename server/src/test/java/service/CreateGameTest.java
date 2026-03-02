package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.exceptions.AlreadyTakenException;
import service.exceptions.UnauthorizedException;
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
    void createGameWithFakeAuthToken() throws Exception {

        CreateGameService service = new CreateGameService(new MemoryGameDAO(), new MemoryAuthDAO());
        CreateRequest request = new CreateRequest("firstGame", "fakeAuthToken");

        assertThrows(UnauthorizedException.class, ()-> service.createGame(request));
    }

}
