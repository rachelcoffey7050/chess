package service.exceptions;

import static service.exceptions.ResponseException.Code.BadRequest;
import static service.exceptions.ResponseException.Code.Forbidden;

public class BadRequestException extends ResponseException{
    public BadRequestException(String message) {
        super(BadRequest, message);
    }
}
