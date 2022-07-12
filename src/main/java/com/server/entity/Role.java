package com.server.entity;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс-сущность для создания таблицы с ролями в БД.
 *
 * @author Aurelius
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private Long ID;
    private String roleName;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(Long ID) {
        this.ID = ID;
    }

    public Role(Long ID, String roleName) {
        this.ID = ID;
        this.roleName = roleName;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
