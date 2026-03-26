package chess.exceptions;

import static chess.exceptions.ResponseException.Code.BadRequest;

public class BadRequestException extends ResponseException{
    public BadRequestException(String message) {
        super(BadRequest, message);
    }
}
