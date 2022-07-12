package com.server.security.JWT;

import com.server.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Класс для генерации JWT токенов для авторизации пользователей.
 *
 * @see JWTTokenGenerator#generateJwtToken(Authentication)
 *
 * @author Aurelius
 */
@Component
public class JWTTokenGenerator {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Метод для генерации JWT токена.
     *
     * @param authentication Пользователь который пытается аутентифицироваться в систему.
     * @return JWT токен для авторизации пользователя в системе.
     */
    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
