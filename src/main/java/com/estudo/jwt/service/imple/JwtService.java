package com.estudo.jwt.service.imple;

import com.estudo.jwt.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Thiago Martins
 * @apiNote This class is being developed to learn how to use JWT authentication.
 */
@Service
public class JwtService implements IJwtService{
    @Value("${secret.key}")
    private String SECRET_KEY;

    // função que retorna o email do jwt
    @Override
    public String extractEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // feito para pegar os dados do jwt. O resolver é uma função que é aplicada nas claims
    // para conseguir o dado requisitado. EX: getSubject é aplicado nas claims para pegar a claim subject
    @Override
    public <T> T getClaim(String token, Function<Claims ,T> resolver)  {
        final Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    // extrai todos os dados do jwt
    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignature())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // descriptografar a chave secreta.
    private SecretKey getSignature() {
        byte[] signature = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(signature);
    }

    @Override
    public String gerarToken(Map<String, Object> dados, UserDetails userDetails){
        return Jwts.builder()
                .claims(dados)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60000 * 5)))
                .signWith(getSignature())
                .compact();
    }

    @Override
    public boolean tokenIsValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equalsIgnoreCase(userDetails.getUsername())) && !tokenExpirado(token);
    }
    private boolean tokenExpirado(String token) {
        return getClaim(token, Claims::getExpiration).before(new Date());
    }
}