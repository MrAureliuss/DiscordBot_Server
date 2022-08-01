package com.server.repository;

import com.server.entity.Channel;
import com.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Класс-репозиторий каналов для составления запросов к БД.
 *
 * @author Aurelius
 */
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findChannelByChannelID(String channelID);
    @Query(value = "SELECT * FROM channels WHERE channelID <> '' AND channelID IS NOT NULL AND owner_id = ?1", nativeQuery = true)
    Set<Channel> getChannelsByOwner(User user);
    Channel findChannelByRegistrationUUIDToken(String UUID);
}
