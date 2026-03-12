package Database;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.exceptions.ResponseException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUnitTests {

    @Test void updateGameNegative() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        ChessGame chessGame = new ChessGame();
        chessGame.makeMove(new ChessMove(new ChessPosition(2, 4), new ChessPosition(3,4), null));
        GameData game = new GameData(1, "me", "you", "name1", new ChessGame());
        gameDAO.addGame(game);
        gameDAO.updateGame(new GameData(1, "me", "you", "name1", chessGame));
        chessGame.makeMove(new ChessMove(new ChessPosition(7, 2), new ChessPosition(6,2), null));

        GameData found = gameDAO.findGame(1);

        assertNotEquals(chessGame, found.game());
    }

    @Test void updateGamePositive() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        ChessGame chessGame = new ChessGame();
        chessGame.makeMove(new ChessMove(new ChessPosition(2, 4), new ChessPosition(3,4), null));
        GameData game = new GameData(1, "me", "you", "name1", new ChessGame());
        gameDAO.addGame(game);
        gameDAO.updateGame(new GameData(1, "me", "you", "name1", chessGame));

        GameData found = gameDAO.findGame(1);

        assertEquals(chessGame, found.game());
    }

    @Test
    public void getGamesNegative() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        java.util.HashMap<Integer, GameData> games = gameDAO.getGames();

        assertEquals(0, games.size());
    }

    @Test
    public void getGamesPositive() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(1, "me", "you", "name1", chessGame);
        gameDAO.addGame(game);
        GameData game2 = new GameData(2, "me", "you", "name2", new ChessGame());
        gameDAO.addGame(game2);

        java.util.HashMap<Integer, GameData> games = gameDAO.getGames();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    public void deleteAllGamesPositive() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        GameData game = new GameData(1, "me", "you", "name1", new ChessGame());
        GameData game2 = new GameData(2, "you", "me", "name2", new ChessGame());

        gameDAO.addGame(game);
        gameDAO.addGame(game2);

        gameDAO.deleteAll();

        GameData found = gameDAO.findGame(game.gameID());

        assertNull(found);
    }


    @Test
    public void addGameNegative() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        GameData game = new GameData(450, "me", "you", "name1", new ChessGame());
        gameDAO.addGame(game);
        GameData game2 = new GameData(90, "me", "you", "name1", new ChessGame());
        gameDAO.addGame(game2);

        GameData game1 = gameDAO.findGame(1);
        GameData game_ = gameDAO.findGame(450);

        assertNotNull(game1);
        assertNull(game_);
    }

    @Test
    public void findGameNegative() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        GameData found = gameDAO.findGame(100);

        assertNull(found);
    }

    @Test
    public void findGamePositive() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(1, "me", "you", "name1", chessGame);
        gameDAO.addGame(game);
        GameData game2 = new GameData(2, "me", "you", "name2", new ChessGame());
        gameDAO.addGame(game2);

        GameData found = gameDAO.findGame(game.gameID());

        assertNotNull(found);
        assertEquals(chessGame, found.game());
    }

    @Test
    public void addGamePositive() throws Exception {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteAll();

        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(1, "me", "you", "name1", chessGame);
        gameDAO.addGame(game);

        GameData found = gameDAO.findGame(game.gameID());

        assertNotNull(found);
        assertEquals(chessGame, found.game());
    }


    @Test
    public void deleteAllAuthsPositive() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData auth = new AuthData("rachel", "testAuth");
        AuthData auth2 = new AuthData("rachel's secret twin", "testAuth2");

        authDAO.addAuthData(auth);
        authDAO.addAuthData(auth2);

        authDAO.deleteAll();

        AuthData found = authDAO.findAuth(auth.authToken());

        assertNull(found);
    }


    @Test
    public void addAuthNegative() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData auth = new AuthData("rachel", "fakeAuth");
        authDAO.addAuthData(auth);
        AuthData auth2 = new AuthData("rachel", "fakeAuth");

        authDAO.findAuth(auth.authToken());

        assertThrows(DataAccessException.class, () -> authDAO.addAuthData(auth2));
    }

    @Test
    public void findAuthNegative() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData found = authDAO.findAuth("testAuth");

        assertNull(found);
    }

    @Test
    public void findAuthPositive() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData auth = new AuthData("rachel", "fakeAuth");
        authDAO.addAuthData(auth);
        AuthData auth2 = new AuthData("rachel's secret twin", "fakeAuth2");
        authDAO.addAuthData(auth2);

        AuthData found = authDAO.findAuth(auth.authToken());

        assertNotNull(found);
        assertEquals("rachel", found.username());
    }

    @Test
    public void addAuthPositive() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData auth = new AuthData("rachel", "fakeAuth");
        authDAO.addAuthData(auth);

        AuthData found = authDAO.findAuth(auth.authToken());

        assertNotNull(found);
        assertEquals("rachel", found.username());
    }

    @Test
    public void deleteAuthPositive() throws Exception {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAll();

        AuthData auth = new AuthData("rachel", "fakeAuth");
        AuthData auth2 = new AuthData("rachel's secret twin", "fakeAuth2");
        authDAO.addAuthData(auth);
        authDAO.addAuthData(auth2);

        authDAO.deleteAuth(auth2);
        AuthData found = authDAO.findAuth(auth.authToken());
        AuthData notFound = authDAO.findAuth(auth2.authToken());

        assertNull(notFound);
        assertNotNull(found);
        assertEquals("rachel", found.username());
    }

    // tests both findUser and adduser and by necessity.
    // I can't find a name I haven't added or know if I added a name without finding
    @Test
    public void addAndFindUserPositive() throws Exception {
        SQLUserDAO userDAO = new SQLUserDAO();
        userDAO.deleteAll();

        UserData user = new UserData("rachel", "hashedPass", "email@example.com");
        userDAO.addUser(user);

        UserData found = userDAO.findUser(user);

        assertNotNull(found);
        assertEquals("rachel", found.username());
        assertEquals("hashedPass", found.password());
    }

    @Test
    public void adduserNegative() throws Exception {
        SQLUserDAO userDAO = new SQLUserDAO();
        userDAO.deleteAll();

        UserData user = new UserData("rachel", "hashedPass", "email@example.com");
        userDAO.addUser(user);

        assertThrows(DataAccessException.class, () -> userDAO.addUser(user));
    }

    @Test
    public void findUserNegative() throws Exception {
        SQLUserDAO userDAO = new SQLUserDAO();
        userDAO.deleteAll();

        UserData user = new UserData("rachel", "hashedPass", "email@example.com");

        UserData found = userDAO.findUser(user);

        assertNull(found);
    }

    @Test
    public void deleteAllUsersPositive() throws Exception {
        SQLUserDAO userDAO = new SQLUserDAO();
        userDAO.deleteAll();

        UserData user = new UserData("rachel", "hashedPass", "email@example.com");
        UserData user2 = new UserData("rachel's secret twin", "hashedPass", "email@example.com");

        userDAO.addUser(user);
        userDAO.addUser(user2);

        userDAO.deleteAll();

        UserData found = userDAO.findUser(user);

        assertNull(found);
    }


}
