package com.estudo.jwt.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    public String extractEmail(String token);

    String gerarToken(Map<String, Object> dados, UserDetails userDetails);

    boolean tokenIsValid(String token, UserDetails userDetails);

    <T> T getClaim(String token, Function<Claims,T> resolver);
}
