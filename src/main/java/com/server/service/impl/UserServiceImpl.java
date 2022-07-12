package com.server.service.impl;

import com.server.entity.Role;
import com.server.entity.User;
import com.server.repository.UserRepository;
import com.server.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Класс для регистрации пользователей в БД .
 *
 * @see UserServiceImpl#addUser(User)
 * @see UserServiceImpl#deleteUser(Long)
 * @see UserServiceImpl#loadUserByUsername(String)
 *
 * @author Aurelius
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Метод для добавления пользователя в БД.
     * При работе метода, пароль кодируется при помощи инстанса BCryptPasswordEncoder.
     *
     * @param user Объект, хранящий информацию о пользователе, которого предстоит зарегистрировать.
     * @return true если пользователь удачно зарегистрирован или false при неудачной регистрации.
     */
    @Override
    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername()); // Инстанс возможного юзера из ДБ по имени.

        if (userFromDB != null) {
            return false; // Такой пользователь уже существует.
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

    /**
     * Метод для удаления пользователя из БД.
     *
     * @param ID Индентификатор пользовател которого предстоит удалить.
     * @return true при удачном удалении пользователя или false при неудачном.
     */
    @Override
    public boolean deleteUser(Long ID) {
        if (userRepository.findById(ID).isPresent()) {
            userRepository.deleteById(ID);
            return true;
        }

        return false;
    }

    /**
     * Метод для поиска пользователя из БД по его username.
     *
     * @param username Имя пользователя, которого надо найти.
     * @return Инстанс User при удачном нахождении пользователя или UsernameNotFoundException при неудачном.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return user;
    }
}
