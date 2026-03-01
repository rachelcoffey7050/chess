package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Test;
import service.requestandresult.DeleteRequest;
import service.requestandresult.DeleteResult;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteServiceTest {

    @Test
    void deleteSuccess() throws Exception {

        DeleteService service = new DeleteService(new MemoryUserDAO(), new MemoryAuthDAO(), new MemoryGameDAO());

        DeleteRequest request = new DeleteRequest();

        DeleteResult result = service.delete(request);

        assertNotNull(result);
    }
}
