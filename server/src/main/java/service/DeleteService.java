package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
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

    public DeleteResult delete(DeleteRequest request) {
        userDAO.deleteAll();
        authDAO.deleteAll();
        gameDAO.deleteAll();
        return new DeleteResult();
    }
}
