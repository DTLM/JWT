package com.estudo.jwt.exception;

public class UserExistsException extends Exception {

    public UserExistsException(String message) {
        super(message);
    }
}
