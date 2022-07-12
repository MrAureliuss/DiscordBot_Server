package com.server.repository;

import com.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Класс-репозиторий ролей для составления запросов к БД.
 *
 * @author Aurelius
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
