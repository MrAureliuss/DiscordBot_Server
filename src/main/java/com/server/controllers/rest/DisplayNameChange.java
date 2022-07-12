package com.server.controllers.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.service.impl.ChannelServiceImpl;
import com.server.utils.exceptions.ChannelOwningException;
import com.server.utils.exceptions.ChannelWithSameDisplayNameAlreadyExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс-контроллер отвечающий за изменение дисплейного название канала.

 * @see DisplayNameChange#changeDisplayName(MultiValueMap)
 *
 * @author Aurelius
 */
@RestController
public class DisplayNameChange {
    @Autowired
    ChannelServiceImpl channelRepository;

    /**
     * Метод для изменения дисплейного названия канала.
     *
     * @param objectNode Объект, хранящий тело запроса пришедшее с клиента.
     * @return Ошибку 400/409 при неудаче(канал с таким дисплейным именем уже существует или канал не принадлежит пользователю) или 200 при успехе
     * @throws ChannelWithSameDisplayNameAlreadyExist Если у пользователя уже есть канал с таким дисплейным именем.
     * @throws ChannelOwningException Если канал с этим ID не принадлежит пользователю.
     */
    @PostMapping("/change_displayName")
    public ResponseEntity<String> changeDisplayName(@RequestParam MultiValueMap<String, String> objectNode) throws ChannelWithSameDisplayNameAlreadyExist, ChannelOwningException {
        try {
            channelRepository.changeDisplayName(objectNode.get("channelID").toString().trim().replaceAll("\\[", "").replaceAll("]",""),
                    objectNode.get("newDisplayName").toString().trim().replaceAll("\\[", "").replaceAll("]",""));
            return new ResponseEntity<>("Успех! Дисплейное название канала успешно изменено!", HttpStatus.OK);
        } catch (ChannelWithSameDisplayNameAlreadyExist channelWithSameDisplayNameAlreadyExist) {
            return new ResponseEntity<>(channelWithSameDisplayNameAlreadyExist.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ChannelOwningException channelOwningException) {
            return new ResponseEntity<>(channelOwningException.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
