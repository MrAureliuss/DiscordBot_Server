package com.server.DTO;

import java.time.LocalDateTime;

/**
 * Класс-DTO для составления на его основе объекта, который будет предоставлен клиенту.
 *
 * @author Aurelius
 */
public class BlacklistedUserDTO {
    private String userName;
    private LocalDateTime dateTime;

    public BlacklistedUserDTO(String userName, LocalDateTime dateTime) {
        this.userName = userName;
        this.dateTime = dateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
