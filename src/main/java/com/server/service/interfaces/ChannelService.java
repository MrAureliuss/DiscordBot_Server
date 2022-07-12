package com.server.service.interfaces;

import com.server.utils.exceptions.*;

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
    boolean changeDisplayName(String channelID, String newDisplayName) throws ChannelWithSameDisplayNameAlreadyExist, ChannelOwningException;
}
