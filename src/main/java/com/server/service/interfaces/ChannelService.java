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
    boolean submitChannel(String UUID, String channelID) throws ChannelWithSameIDAlreadyExistException, UUIDTokenNotExistException;
    boolean changeDisplayName(String channelID, String newDisplayName) throws ChannelWithSameDisplayNameAlreadyExist, ChannelOwningException;
    boolean deleteChannel(String channelID) throws ChannelOwningException;
}
