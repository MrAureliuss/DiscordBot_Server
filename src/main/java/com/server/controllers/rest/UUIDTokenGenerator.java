package com.server.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Класс-контроллер для генерации UUID токенов.
 * Токен генерируется и возвращается клиенту при обращении на URL "/generate_token" посредством метода generateChannelToken()
 *
 * @see UUIDTokenGenerator#generateChannelToken()
 *
 * @author Aurelius
 */
@RestController
public class UUIDTokenGenerator {
    /**
     * Метод-обработчик GET запроса для получения UUID токена при обращении на URL /create_channel.
     *
     * @return Возвращает UUID токен в String.
     */
    @GetMapping("/generate_token")
    public String generateChannelToken() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
