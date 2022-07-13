package com.server.controllers.rest;

import com.server.service.impl.ChannelServiceImpl;
import com.server.utils.exceptions.ChannelOwningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelDelete {
    @Autowired
    ChannelServiceImpl channelService;

    @PostMapping("/channel_delete")
    public ResponseEntity<String> deleteChannel(@RequestBody(required = false) String channelID) {
        try {
            channelService.deleteChannel(channelID.replace("=", ""));
            return new ResponseEntity<>("Успех! Канал успешно удален из вашего списка каналов.", HttpStatus.OK);
        } catch (ChannelOwningException channelOwningException) {
            return new ResponseEntity<>(channelOwningException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>("Ошибка! Не выбран канал для удаления!", HttpStatus.CONFLICT);
        }
    }
}
