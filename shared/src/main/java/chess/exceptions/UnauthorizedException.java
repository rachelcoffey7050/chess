package chess.exceptions;

import static chess.exceptions.ResponseException.Code.Unauthorized;

public class UnauthorizedException extends ResponseException{
    public UnauthorizedException(String message) {
        super(Unauthorized, message);
    }
}
