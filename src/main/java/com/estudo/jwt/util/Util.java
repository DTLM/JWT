package com.estudo.jwt.util;

import com.estudo.jwt.exception.AssinaturaInvalidaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

public class Util {

    @Value("${secret.key}")
    private static String SECRET_KEY;

    private Util() {}
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String gerarCodigoSeguranca() {
        StringBuilder codigo = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < 3; i++) {
            var index = random.nextInt(CHARACTERS.length());
            codigo.append(CHARACTERS.charAt(index));
        }
        return codigo.toString();
    }

    // para quem não souber usar bem o Claims do io.jsonwebtoken
    public static Map<String, Object> decodeJwtToken(@Nonnull String token) throws Exception {
        // primeiro passo: dar um split no token para separar o header, payload e signature
        String[] parts = token.split("\\.");

        // criar um validador de assinatura para ver se o token é válido
        boolean assinaturaValida = validarAssinatura(parts);
        if(!assinaturaValida){
            throw new AssinaturaInvalidaException("Assinatura do token é inválida");
        }
        // pegue o 2 item, ele é o payload do JWT
        String payload = parts[1];

        // Decode o payload da Base64
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));

        // Use a biblioteca Jacksonou outra biblioteca para converter a string para um map
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(decodedPayload, Map.class);
    }

    /**
     * Para referencia da biblioteca Mac, usar o link:
     * {@link <a href="https://docs.oracle.com/cd/E14571_01/apirefs.1111/e10668/oracle/security/crypto/core/HMAC.html">...</a>}
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    // Mac é a biblioteca java que é usada para encriptografar dados em MD5 e SHA
    public static boolean validarAssinatura(@Nonnull String[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        String headerPayload = data[0] + "." + data[1];
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(data[2].getBytes(), "HmacSHA256");
        hmacSha256.init(secretKeySpec);

        byte[] hmacBytes = hmacSha256.doFinal(headerPayload.getBytes());
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(hmacBytes);
        return signature.equals(data[2]);
    }
}
