package com.estudo.jwt.exception;

public class TokenNotFoundException extends Exception {
    public TokenNotFoundException(String message){
        super(message);
    }
}
