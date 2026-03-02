package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.exceptions.UnauthorizedException;
import service.requestandresult.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListServiceTest {
     @Test
    void listSuccess() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        LoginResult resultL = serviceL.login(requestL);


        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateRequest request = new CreateRequest("firstGame", resultL.authToken());
        service.createGame(request);
        CreateRequest request2 = new CreateRequest("second game", resultL.authToken());
        service.createGame(request2);

        ListService serviceLi = new ListService(gameDAO, authDAO);
        ListRequest requestLi = new ListRequest(resultL.authToken());
        ListResult result = serviceLi.listGames(requestLi);

        assertEquals(2, result.games().size());

    }

    @Test
    void listUnauthorized() throws Exception {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        LoginResult resultL = serviceL.login(requestL);

        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateRequest request = new CreateRequest("firstGame", resultL.authToken());
        service.createGame(request);

        ListService serviceLi = new ListService(gameDAO, authDAO);
        ListRequest requestLi = new ListRequest("fakeAuthToken");

        assertThrows(UnauthorizedException.class, ()->serviceLi.listGames(requestLi));

    }
}
