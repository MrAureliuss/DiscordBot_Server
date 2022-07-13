package com.server.controllers.rest;

import com.server.service.impl.ChannelServiceImpl;
import com.server.utils.exceptions.ChannelOwningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс-контроллер отвечающий за удаление канала из БД.

 * @see ChannelDelete#deleteChannel(String channelID)
 *
 * @author Aurelius
 */
@RestController
public class ChannelDelete {
    @Autowired
    ChannelServiceImpl channelService;

    /**
     * Метод для удаления канала из БД.
     *
     * @param channelID Канал, который надо будет удалить из БД
     * @return ResponseEntity<>, ошибку 400/422 при неудаче и 200 при успешном удалении.
     */
    @PostMapping("/channel_delete")
    public ResponseEntity<String> deleteChannel(@RequestBody(required = false) String channelID) {
        try {
            channelService.deleteChannel(channelID.replace("=", ""));
            return new ResponseEntity<>("Успех! Канал успешно удален из вашего списка каналов.", HttpStatus.OK);
        } catch (ChannelOwningException channelOwningException) {
            return new ResponseEntity<>(channelOwningException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>("Ошибка! Не выбран канал для удаления!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
