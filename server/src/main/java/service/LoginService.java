package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import service.exceptions.BadRequestException;
import service.exceptions.ResponseException;
import service.exceptions.UnauthorizedException;
import service.requestandresult.LoginRequest;
import service.requestandresult.LoginResult;

import java.util.UUID;

public class LoginService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public LoginService(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public LoginResult login(LoginRequest request)
            throws DataAccessException, ResponseException {

        if (request.username()==null || request.password()==null){
            throw new BadRequestException("Error: bad request");
        }
        UserData user = new UserData(request.username(), request.password(), "filler-email");
        UserData newUser = userDAO.findUser(user);
        if (newUser == null || BCrypt.checkpw(request.password(), newUser.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        String authToken = generateToken();
        AuthData authData = new AuthData(user.username(), authToken);
        authDAO.addAuthData(authData);

        return new LoginResult(user.username(), authToken);

    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
