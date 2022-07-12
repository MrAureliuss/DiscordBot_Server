package com.server.repository;

import com.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Size;

/**
 * Класс-репозиторий пользователей для составления запросов к БД.
 *
 * @author Aurelius
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Size(min = 5) String username);
}
