package com.server.service.interfaces;

import com.server.entity.User;

/**
 * Контракт, который имплементирует класс UserServiceImpl.
 *
 * @see com.server.service.impl.UserServiceImpl
 *
 * @author Aurelius
 */
public interface UserService {
    boolean addUser(User user);
    boolean deleteUser(Long ID);
}
