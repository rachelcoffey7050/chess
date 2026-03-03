package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import org.junit.jupiter.api.Test;
import service.exceptions.UnauthorizedException;
import service.requestandresult.LoginRequest;
import service.requestandresult.LoginResult;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    @Test
    void loginSuccess() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService service = new LoginService(userDAO, authDAO);

        LoginRequest request = new LoginRequest("rachel", "pw");

        LoginResult result = service.login(request);

        assertEquals("rachel", result.username());
        assertNotNull(result.authToken());

    }

    @Test
    void loginWrongPassword() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        LoginService service = new LoginService(userDAO, authDAO);
        RegisterService serviceR = new RegisterService(userDAO, authDAO);

        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        RegisterResult result = serviceR.register(rRequest);

        LoginRequest request = new LoginRequest("rachel", "password");

        assertThrows(UnauthorizedException.class, () -> service.login(request));
    }
}

