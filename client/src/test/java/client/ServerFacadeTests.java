package client;

import chess.ChessGame;
import chess.requestandresult.*;
import org.junit.jupiter.api.*;
import server.Server;
import chess.exceptions.ResponseException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void resetServer() throws ResponseException{
        DeleteRequest req = new DeleteRequest();
        facade.delete(req);
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        assert(result.username().equals("user"));
        assertNotNull(result.authToken());
    }

    @Test
    public void registerNegativeTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        //register twice
        assertThrows(ResponseException.class, () -> {facade.register(req);});
    }

    @Test
    public void loginPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest request = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(request, result.authToken());

        assert(resultL.username().equals("user"));
        assertNotNull(result.authToken());
    }

    @Test
    public void loginNegativeTest() throws ResponseException {
        LoginRequest req = new LoginRequest("user", "password");
        assertThrows(ResponseException.class, () -> {facade.login(req, "fake token");});
    }

    @Test
    public void logoutPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest request = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(request, result.authToken());

        facade.logout(result.authToken());
        assert(resultL.username().equals("user"));
        assertNotNull(result.authToken());
    }

    @Test
    public void logoutNegativeTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        facade.register(req);
        assertThrows(ResponseException.class, () -> {facade.logout("fake token");});
    }

    @Test
    public void createPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest req2 = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(req2, result.authToken());

        CreateRequest request = new CreateRequest("new Game", resultL.authToken());
        CreateResult resultC = facade.createGame(request, result.authToken());

        assertNotNull(resultC.gameID());
    }

    @Test
    public void createNegativeTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest req2 = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(req2, result.authToken());

        CreateRequest request = new CreateRequest("game", resultL.authToken());
        assertThrows(ResponseException.class, () -> {facade.createGame(request, "incorrect auth token here");});
    }

    @Test
    public void joinPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest req2 = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(req2, result.authToken());

        CreateRequest request = new CreateRequest("new Game", resultL.authToken());
        CreateResult resultC = facade.createGame(request, result.authToken());

        JoinRequest requestJ = new JoinRequest(ChessGame.TeamColor.WHITE, resultC.gameID(), resultL.authToken());
        JoinResult resultJ = facade.joinGame(requestJ, result.authToken());

        assertNotNull(resultJ);
    }

    @Test
    public void joinNegativeTest() throws ResponseException {
        JoinRequest requestJ = new JoinRequest(ChessGame.TeamColor.WHITE, 0, "fake person");

        assertThrows(ResponseException.class, () -> {facade.joinGame(requestJ, "fake person");});
    }

    @Test
    public void listPositiveTest() throws ResponseException {
        RegisterRequest req = new RegisterRequest("user", "password", "email");
        RegisterResult result = facade.register(req);

        LoginRequest req2 = new LoginRequest("user", "password");
        LoginResult resultL = facade.login(req2, result.authToken());

        CreateRequest request = new CreateRequest("new Game", resultL.authToken());
        facade.createGame(request, result.authToken());

        ListResult res = facade.listGames(resultL.authToken());

        assertNotNull(res);
    }

    @Test
    public void listNegativeTest() throws ResponseException {
        assertThrows(ResponseException.class, () -> {facade.listGames("fake person");});
    }



}
