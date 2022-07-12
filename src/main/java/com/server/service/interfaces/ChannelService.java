package com.server.service.interfaces;

import com.server.utils.exceptions.ChannelWithSameIDAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenAlreadyExistException;
import com.server.utils.exceptions.UUIDTokenNotExistException;

/**
 * Контракт, который имплементирует класс ChannelServiceImpl.
 *
 * @see com.server.service.impl.ChannelServiceImpl
 *
 * @author Aurelius
 */
public interface ChannelService {
    boolean addChannel(String userUUID) throws UUIDTokenNotExistException, UUIDTokenAlreadyExistException;
    boolean deleteChannel(Long ID);
    boolean submitChannel(String UUID, String channelID) throws ChannelWithSameIDAlreadyExistException, UUIDTokenNotExistException;
}
