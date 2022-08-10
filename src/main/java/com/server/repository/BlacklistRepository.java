package com.server.repository;


import com.server.entity.BlacklistedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Класс-репозиторий blacklist для составления запросов к БД.
 *
 * @author Aurelius
 */
public interface BlacklistRepository extends JpaRepository<BlacklistedUser, Long> {
    Set<BlacklistedUser> getBlacklistedUserByChannelID(String channelID);
    long deleteBlacklistedUserByUserIDAndChannelID(Long userID, String channelID);
    long countBlacklistedUserByUserIDAndChannelID(Long userID, String channelID);
}
