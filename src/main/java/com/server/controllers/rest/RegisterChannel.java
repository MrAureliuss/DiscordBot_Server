package com.server.controllers.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.service.impl.ChannelServiceImpl;
import com.server.utils.exceptions.ChannelWithSameIDAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Класс-контроллер отвечающий за регистрацию каналов.
 * Регистрация канала состоит из двух частей - предварительной регистрации и подтверждении канала.
 * Предварительная регистрация - метод createChannel(String userUUID).
 * Подтверждение канала - метод submitChannel(ObjectNode objectNode).
 *
 * @see RegisterChannel#createChannel(String userUUID)
 * @see RegisterChannel#submitChannel(ObjectNode objectNode)
 *
 * @author Aurelius
 */
@RestController
public class RegisterChannel {
    @Autowired
    ChannelServiceImpl channelRepository;

    /**
     * Метод-обработчик POST запроса для предварительной регистрации канала при обращении на URL /create_channel.
     *
     * @param userUUID Уникальный индентификатор при регистрации канала.
     * @return ResponseEntity<>(String, HttpStatus.BAD_REQUEST) при неверных данных или ResponseEntity<>(String, HttpStatus.OK) при верных данных.
     */
    @PostMapping("/create_channel")
    public ResponseEntity<String> createChannel(@RequestBody String userUUID) {
        try {
            userUUID = UUID.fromString(userUUID.replace("=", "")).toString(); // Проверка UUID
            channelRepository.addChannel(userUUID);

            return new ResponseEntity<>("Успех! Авторизуйте свой канал с помощью UUID в чате Discord.", HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ResponseEntity<>("Ошибка! UUID не прошел проверку подлинности!", HttpStatus.BAD_REQUEST);
        } catch (UUIDTokenAlreadyExistException uuidTokenExistException) {
            return new ResponseEntity<>("Ошибка! Канал с таким UUID уже существует!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод-обработчик POST запроса для подтверждения регистрации канала при обращении на URL /submit_channel.
     *
     * @param objectNode Объект содержащий параметры UUID и ChannelID.
     * @return ResponseEntity<>(String, HttpStatus.BAD_REQUEST) при неверных данных или ResponseEntity<>(String, HttpStatus.OK) при верных данных.
     */
    @PostMapping("/submit_channel")
    public ResponseEntity<String> submitChannel(@RequestBody ObjectNode objectNode) {
        try {
            channelRepository.submitChannel(objectNode.get("UUID").asText().trim(), objectNode.get("channelID").asText().trim());

            return new ResponseEntity<>("Вы успешно подтвердили свой канал Discord!", HttpStatus.OK);
        } catch (UUIDTokenNotExistException uuidTokenNotExistException) {
            return new ResponseEntity<>("Ошибка! Канала с таким UUID не существует!", HttpStatus.BAD_REQUEST);
        } catch (ChannelWithSameIDAlreadyExistException channelWithSameIDAlreadyExistException) {
            return new ResponseEntity<>("Ошибка! Канал с таким уже существует!", HttpStatus.BAD_REQUEST);
        }
    }

}
