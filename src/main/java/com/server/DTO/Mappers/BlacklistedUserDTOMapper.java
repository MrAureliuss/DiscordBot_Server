package com.server.DTO.Mappers;

import com.server.DTO.BlacklistedUserDTO;
import com.server.entity.BlacklistedUser;
import org.springframework.stereotype.Component;

/**
 * Класс-маппер для маппинга класса BlacklistedUser в BlacklistedUserDTO.
 *
 * @author Aurelius
 */
@Component
public class BlacklistedUserDTOMapper {
    public BlacklistedUserDTO mapToBlacklistedUserDTO(BlacklistedUser blacklistedUser) {
        return new BlacklistedUserDTO(blacklistedUser.getUserName(), blacklistedUser.getDateTime());
    }
}