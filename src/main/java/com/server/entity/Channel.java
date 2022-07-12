package com.server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс-сущность для создания таблицы с каналами в БД.
 *
 * @author Aurelius
 */
@Entity
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;

    @NotNull
    private String channelID;

    @NotNull
    @ManyToOne
    private User owner;

    @NotNull
    private String registrationUUIDToken;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getRegistrationUUIDToken() {
        return registrationUUIDToken;
    }

    public void setRegistrationUUIDToken(String registrationUUIDToken) {
        this.registrationUUIDToken = registrationUUIDToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
