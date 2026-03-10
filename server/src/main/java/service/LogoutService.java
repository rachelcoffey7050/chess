package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import service.exceptions.ResponseException;
import service.exceptions.UnauthorizedException;
import service.requestandresult.LogoutRequest;
import service.requestandresult.LogoutResult;

public class LogoutService {
    private final AuthDAO authDAO;

    public LogoutService(AuthDAO authDAO){
        this.authDAO = authDAO;
    }

    public LogoutResult logout(LogoutRequest request) throws DataAccessException, ResponseException {
        String authToken = request.authToken();
        AuthData authData = authDAO.findAuth(authToken);
        if (authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        authDAO.deleteAuth(authData);
        return new LogoutResult();
    }
}
