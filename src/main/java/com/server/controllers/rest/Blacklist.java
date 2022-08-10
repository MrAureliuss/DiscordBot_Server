package com.server.controllers.rest;

import com.server.DTO.BlacklistedUserDTO;
import com.server.service.impl.BlacklistServiceImpl;
import com.server.utils.exceptions.ChannelOwningException;
import com.server.utils.exceptions.DatabaseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Класс-контроллер отвечающий за работу с Blacklist.

 * @see Blacklist#getBlacklistedUsers(String channelID)
 * @see Blacklist#addUserToBlackList(HttpEntity)
 * @see Blacklist#deleteUserFromBlacklist(HttpEntity)
 *
 * @author Aurelius
 */
@RestController
public class Blacklist {
    @Autowired
    BlacklistServiceImpl blacklistService;

    /**
     * Метод для получения списка пользователей которые находятся в blacklist канала.
     *
     * @param channelID Канал, для которого будет происходить выборка пользователей.
     * @return ResponseEntity<>, ошибку 400/422 при неудаче и 200 при успешной отправке списка пользователей.
     */
    @PostMapping("/getBlacklistedUsers")
    public ResponseEntity<?> getBlacklistedUsers(@RequestBody(required = false) String channelID) {
        try {
            Set<BlacklistedUserDTO> blacklistedUsers = blacklistService.getBlacklistedUsersByChannelID(channelID.replace("=", ""));
            return new ResponseEntity<>(blacklistedUsers, HttpStatus.OK);
        } catch (ChannelOwningException channelOwningException) {
            return new ResponseEntity<>(channelOwningException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>("Ошибка! Не выбран канал!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Метод для добавления пользователя в blacklist канала.
     *
     * @param httpEntity Объект в формате JSON, содержащий параметры для последующей работы.
     * @return ResponseEntity<>, ошибку 400/422 при неудаче и 200 при успешном добавлении пользователя в blacklist.
     */
    @PostMapping(value = "/addUserToBlacklist", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUserToBlackList(HttpEntity<String> httpEntity) {
        try {
            blacklistService.addUserToChannelBlacklist(httpEntity.getBody());

            return new ResponseEntity<>("Участник канала успешно добавлен в черный список!", HttpStatus.OK);
        } catch (ChannelOwningException | DatabaseException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>("Ошибка! Не выбран канал!", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (JSONException jsonException) {
            return new ResponseEntity<>(jsonException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException.BadRequest badRequest) {
            return new ResponseEntity<>("Ошибка! Пользователь не найден!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для удаления пользователя из blacklist канала.
     *
     * @param httpEntity Объект в формате JSON, содержащий параметры для последующей работы.
     * @return ResponseEntity<>, ошибку 400/422 при неудаче и 200 при успешном добавлении пользователя в блеклист.
     */
    @PostMapping(value = "/deleteUserFromBlacklist", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUserFromBlacklist(HttpEntity<String> httpEntity) {
        try {
            blacklistService.deleteUserFromBlacklist(httpEntity.getBody());

            return new ResponseEntity<>("Участник канала успешно удален из черного списка!", HttpStatus.OK);
        } catch (ChannelOwningException | DatabaseException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>("Ошибка! Не выбран канал!", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (JSONException jsonException) {
            return new ResponseEntity<>(jsonException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException.BadRequest badRequest) {
            return new ResponseEntity<>("Ошибка! Пользователь не найден!", HttpStatus.BAD_REQUEST);
        }
    }
}
