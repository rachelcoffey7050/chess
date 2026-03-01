package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import service.exceptions.AlreadyTakenException;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;
import dataaccess.UserDAO.*;
import java.util.UUID;

public class RegisterService {

    UserDAO userDAO;

    public RegisterService(){
        userDAO = new MemoryUserDAO();
    }


    public RegisterResult register(RegisterRequest request) throws DataAccessException, AlreadyTakenException {

        // get user
        UserData user = new UserData(request.username(), request.password(), request.email());
        UserData newUser = userDAO.findUser(user);
        if (newUser != null) {
            throw new AlreadyTakenException("username already taken");
        }

        userDAO.addUser(user);

        AuthDAO authDAO = new MemoryAuthDAO();
        String authToken = generateToken();
        AuthData authData = new AuthData(user.username(), authToken);
        authDAO.addAuthData(authData);

        return new RegisterResult(user.username(), authToken);

        }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
    }
