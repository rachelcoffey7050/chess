package service.exceptions;

import static service.exceptions.ResponseException.Code.Forbidden;

public class AlreadyTakenException extends ResponseException{
    public AlreadyTakenException(String message) {
        super(Forbidden, message);
    }
    public AlreadyTakenException(String message, Throwable ex) {
        super(Forbidden, message);
    }
}
