package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.exceptions.ResponseException;
import service.requestandresult.DeleteRequest;
import service.requestandresult.DeleteResult;

public class DeleteService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public DeleteService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public DeleteResult delete(DeleteRequest request) throws ResponseException, DataAccessException {
        try {
            userDAO.deleteAll();
            authDAO.deleteAll();
            gameDAO.deleteAll();
        }
        catch (Exception e){
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Error: Unable to delete: %s", e.getMessage()));
        }
        return new DeleteResult();
    }
}
