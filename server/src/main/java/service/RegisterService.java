package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;
import dataaccess.UserDAO.*;
import java.util.UUID;

public class RegisterService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public RegisterService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }


    public RegisterResult register(RegisterRequest request)
            throws DataAccessException, AlreadyTakenException, BadRequestException {

        // get user
        if (request.username()==null || request.password()==null || request.email()==null){
            throw new BadRequestException("Error: bad request");
        }
        UserData user = new UserData(request.username(), request.password(), request.email());
        UserData newUser = userDAO.findUser(user);
        if (newUser != null) {
            throw new AlreadyTakenException("Error: username already taken");
        }

        userDAO.addUser(user);

        String authToken = generateToken();
        AuthData authData = new AuthData(user.username(), authToken);
        authDAO.addAuthData(authData);

        return new RegisterResult(user.username(), authToken);

        }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
    }
