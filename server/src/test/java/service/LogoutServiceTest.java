package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import org.junit.jupiter.api.Test;
import service.exceptions.UnauthorizedException;
import service.requestandresult.*;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutServiceTest {

    @Test
    void logoutSuccess() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService service1 = new LoginService(userDAO, authDAO);
        LoginRequest request1 = new LoginRequest("rachel", "pw");
        LoginResult result1 = service1.login(request1);
        String authToken = result1.authToken();

        LogoutService service = new LogoutService(authDAO);
        LogoutRequest request = new LogoutRequest(authToken);

        LogoutResult result = service.logout(request);

        assertNotNull(result);

    }

    @Test
    void LogoutWrongAuth() throws Exception {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();


        RegisterService serviceR = new RegisterService(userDAO, authDAO);
        RegisterRequest rRequest = new RegisterRequest("rachel", "pw", "email");
        serviceR.register(rRequest);

        LoginService serviceL = new LoginService(userDAO, authDAO);
        LoginRequest requestL = new LoginRequest("rachel", "pw");
        serviceL.login(requestL);

        LogoutService service = new LogoutService(authDAO);
        LogoutRequest request = new LogoutRequest("fakeAuthToken");

        assertThrows(UnauthorizedException.class, () -> service.logout(request));
    }
}
