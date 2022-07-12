package com.server.security.JWT;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

/**
 * Класс для получения пользователя по его токену.
 *
 * @see JWTUserGetter#getUserNameFromJwtToken(String token)
 *
 * @author Aurelius
 */
public class JWTUserGetter {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    /**
     * Метод для получения инстанса пользователя по его токену.
     *
     * @param token Токен для получения инстанса.
     * @return Инстанс пользователя.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
