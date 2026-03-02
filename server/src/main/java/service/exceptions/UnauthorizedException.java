package service.exceptions;

import static service.exceptions.ResponseException.Code.Forbidden;
import static service.exceptions.ResponseException.Code.Unauthorized;

public class UnauthorizedException extends ResponseException{
    public UnauthorizedException(String message) {
        super(Unauthorized, message);
    }
}
