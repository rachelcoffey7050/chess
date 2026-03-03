package service.exceptions;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResponseException extends Exception {

    public enum Code {
        BadRequest, // 400
        Unauthorized, // 401
        Forbidden, // 403
        ServerError // 500
    }

    final private Code code;

    public ResponseException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public String toJson() {
        return new Gson().toJson(Map.of("message", getMessage(), "status", code));
    }

    public static ResponseException fromJson(String json) {
        var map = new Gson().fromJson(json, HashMap.class);
        var status = Code.valueOf(map.get("status").toString());
        String message = map.get("message").toString();
        return new ResponseException(status, message);
    }

    public int toHttpStatusCode() {
        return switch (code) {
            case ServerError -> 500;
            case BadRequest -> 400;
            case Forbidden -> 403;
            case Unauthorized -> 401;
        };
    }
}

