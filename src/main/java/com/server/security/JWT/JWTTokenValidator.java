package com.server.security.JWT;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * Класс для валидации JWT токенов.
 *
 * @see JWTTokenValidator#validateJWTToken(String authToken)
 *
 * @author Aurelius
 */
public class JWTTokenValidator {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    /**
     * Метод проверяет на правильность токен.
     *
     * @param authToken Токен который нужно валидировать.
     * @return true - если токен прошел проверку, false - при отрицательной валидации токена.
     */
    public boolean validateJWTToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
