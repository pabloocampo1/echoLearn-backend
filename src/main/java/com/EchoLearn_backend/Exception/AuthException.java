package com.EchoLearn_backend.Exception;

public class AuthException extends RuntimeException{
    public AuthException (String message) {
        super(message);
    }
}
