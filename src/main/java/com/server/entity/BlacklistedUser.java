package com.server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс-сущность для создания таблицы с blacklist.
 *
 * @author Aurelius
 */
@Entity
@Table(name = "blacklist")
public class BlacklistedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    @NotNull
    private String channelID;
    @NotNull
    private String userName;

    @NotNull
    private Long userID;

    @NotNull
    private LocalDateTime dateTime;

    public BlacklistedUser() {}

    public BlacklistedUser(String channelID, String userName, Long userID, LocalDateTime dateTime) {
        this.channelID = channelID;
        this.userName = userName;
        this.userID = userID;
        this.dateTime = dateTime;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
