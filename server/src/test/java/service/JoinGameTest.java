package service;

import chess.ChessGame;
import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.exceptions.AlreadyTakenException;
import service.requestandresult.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinGameTest {

    @Test
    void joinSuccess() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        LoginResult resultL = serviceL.login(requestL);


        CreateGameService serviceC = new CreateGameService(gameDAO, authDAO);
        CreateRequest requestC = new CreateRequest("firstGame", resultL.authToken());
        CreateResult resultC = serviceC.createGame(requestC);

        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinRequest request = new JoinRequest(resultL.authToken(), ChessGame.TeamColor.WHITE, resultC.gameID());
        JoinResult result = service.joinGame(request);

        assertNotNull(result);
    }

    @Test
    void joinAlreadyTaken() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        LoginResult resultL = serviceL.login(requestL);


        CreateGameService serviceC = new CreateGameService(gameDAO, authDAO);
        CreateRequest requestC = new CreateRequest("firstGame", resultL.authToken());
        CreateResult resultC = serviceC.createGame(requestC);

        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinRequest request1 = new JoinRequest(resultL.authToken(), ChessGame.TeamColor.WHITE, resultC.gameID());
        service.joinGame(request1);
        JoinRequest request2 = new JoinRequest(resultL.authToken(), ChessGame.TeamColor.WHITE, resultC.gameID());

        assertThrows(AlreadyTakenException.class, () -> service.joinGame(request2));
    }
}
