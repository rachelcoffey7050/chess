package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import chess.exceptions.AlreadyTakenException;
import chess.exceptions.BadRequestException;
import chess.exceptions.ResponseException;
import chess.requestandresult.RegisterRequest;
import chess.requestandresult.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public RegisterService(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }


    public RegisterResult register(RegisterRequest request)
            throws DataAccessException, ResponseException {

        // get user
        if (request.username()==null || request.password()==null || request.email()==null){
            throw new BadRequestException("Error: bad request");
        }
        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        UserData user = new UserData(request.username(), hashedPassword, request.email());
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
