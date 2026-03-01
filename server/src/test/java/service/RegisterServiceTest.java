package service;

import org.junit.jupiter.api.Test;
import service.exceptions.AlreadyTakenException;
import service.requestandresult.RegisterRequest;
import service.requestandresult.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    @Test
    void registerSuccess() throws Exception {

        RegisterService service = new RegisterService();

        RegisterRequest request = new RegisterRequest("rachel", "pw", "email");

        RegisterResult result = service.register(request);

        assertEquals("rachel", result.username());
        assertNotNull(result.authToken());

    }

    @Test
    void registerDuplicateUser() throws Exception {

        RegisterService service = new RegisterService();

        RegisterRequest request = new RegisterRequest("rachel", "pw", "email");
        RegisterResult result1 = service.register(request);

        assertThrows(AlreadyTakenException.class, () -> service.register(request));
    }
}
