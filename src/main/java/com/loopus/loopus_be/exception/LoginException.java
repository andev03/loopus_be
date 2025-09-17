package com.loopus.loopus_be.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message){
        super(message);
    }
}
