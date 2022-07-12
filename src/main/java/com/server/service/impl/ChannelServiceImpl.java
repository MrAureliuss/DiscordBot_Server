package com.server.service.impl;

import com.server.entity.Channel;
import com.server.entity.User;
import com.server.repository.ChannelRepository;
import com.server.service.interfaces.ChannelService;
import com.server.utils.exceptions.ChannelWithSameIDAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Класс для регистрации канала в БД и привязки его к админу на сайте.
 * Регистрация канала состоит из двух частей - предварительной регистрации и подтверждении канала.
 * Предварительная регистрация - метод addChannel(String userUUID).
 * Подтверждение канала - метод submitChannel(String UUID, String channelID).
 *
 * @see ChannelServiceImpl#addChannel(String userUUID)
 * @see ChannelServiceImpl#submitChannel(String UUID, String channelID)
 *
 * @author Aurelius
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    ChannelRepository channelRepository;

    /**
     * Метод для предварительной регистрации канала в БД.
     * На момент предварительной регистрации в БД заносятся все поля исключая channelID.
     * Поле channelID заносится в БД при помощи метода submitChannel(), после команды из чата канала Discord.
     *
     * @param userUUID Уникальный индентификатор при регистрации канала.
     * @return false при неудачной попытке регистрации канала или true при удачной.
     * @throws UUIDTokenAlreadyExistException Если канал в БД с таким UUID уже существует.
     */
    @Override
    public boolean addChannel(String userUUID) throws UUIDTokenAlreadyExistException {
        Channel channelFromDB = channelRepository.findChannelByRegistrationUUIDToken(userUUID);
        if (channelFromDB != null) {
            throw new UUIDTokenAlreadyExistException("Канал с таким UUID уже существует!");
        }

        Channel newChannel = new Channel();
        newChannel.setChannelID("");
        newChannel.setActive(false);
        newChannel.setLocalDateTime(LocalDateTime.now());
        newChannel.setRegistrationUUIDToken(userUUID);
        newChannel.setOwner((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        channelRepository.save(newChannel);
        return true;
    }

    /**
     * Метод для подтверждения регистрации канала в БД.
     * При подтверждении в БД заносятся поле channelID, обновляются поля localDateTime и active.
     * Остальные поля заносятся методом addChannel(String userUUID), в момент предварительной регистрации канала.
     *
     * @param UUID Уникальный индентификатор при регистрации канала.
     * @param channelID Индентификатор канала дискорда, получаемый из команды *authorize в чате дискорда.
     * @return false при неудачной попытке подтверждения канала и true при удачной.
     * @throws UUIDTokenNotExistException Если UUID присланный из чата дискорда не существует.
     * @throws ChannelWithSameIDAlreadyExistException Если дискорд-канал с таким ID уже существует.
     */
    @Override
    public boolean submitChannel(String UUID, String channelID) throws UUIDTokenNotExistException, ChannelWithSameIDAlreadyExistException {
        Channel channelByRegistrationUUIDToken = channelRepository.findChannelByRegistrationUUIDToken(UUID);
        Channel channelByChannelID = channelRepository.findChannelByChannelID(channelID);

        if (channelByRegistrationUUIDToken == null) {
            throw new UUIDTokenNotExistException("Канал с таким UUID не существует!");
        }

        if (channelByChannelID != null) {
            throw new ChannelWithSameIDAlreadyExistException("Канал с таким ID уже существует!");
        }

        channelByRegistrationUUIDToken.setChannelID(channelID);
        channelByRegistrationUUIDToken.setLocalDateTime(LocalDateTime.now());
        channelByRegistrationUUIDToken.setActive(true);

        channelRepository.save(channelByRegistrationUUIDToken);
        return true;
    }


    @Override
    public boolean deleteChannel(Long ID) {
        return false;
    }
}
