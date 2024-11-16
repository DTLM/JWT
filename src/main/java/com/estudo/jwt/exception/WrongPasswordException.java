package com.estudo.jwt.exception;

public class WrongPasswordException extends Exception  {
    public WrongPasswordException(String message) {
        super(message);
    }
}
