package com.server.service.interfaces;

import com.server.DTO.BlacklistedUserDTO;
import com.server.utils.exceptions.ChannelOwningException;
import com.server.utils.exceptions.DatabaseException;
import org.json.JSONException;

import java.util.Set;

/**
 * Контракт, который имплементирует класс BlacklistServiceImpl.
 *
 * @see com.server.service.impl.BlacklistServiceImpl
 *
 * @author Aurelius
 */
public interface BlacklistService {
    Set<BlacklistedUserDTO> getBlacklistedUsersByChannelID(String channelID) throws ChannelOwningException;
    boolean addUserToChannelBlacklist(String jsonBody) throws ChannelOwningException, DatabaseException, JSONException;
    boolean deleteUserFromBlacklist(String jsonBody) throws ChannelOwningException, DatabaseException, JSONException;
}
