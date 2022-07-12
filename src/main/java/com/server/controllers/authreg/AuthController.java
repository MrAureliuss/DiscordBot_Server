package com.server.controllers.authreg;

import com.server.entity.User;
import com.server.security.JWT.JWTTokenGenerator;
import com.server.utils.responses.AJAXResponse;
import com.server.utils.responses.JWTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Класс-контроллер отвечающий за авторизацию пользователей.
 * Авторизация пользователя происходит при обращении на URL /sign_in посредством метода authUser(User newUser).
 *
 * @see AuthController#authUser(User newUser)
 *
 * @author Aurelius
 */
@Controller
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenGenerator jwtTokenGenerator;

    /**
     * Метод-обработчик POST запроса для авторизации пользователей при обращении на URL /sign_in.
     *
     * @param newUser Объект, который хранит информацию о авторизуемом пользователе.
     * @return ResponseEntity<>(AJAXResponse, HTTPStatus.UNAUTHORIZED) при неверных данных или ResponseEntity<>(JWTResponse, HttpStatus.OK) при верных данных.
     */
    @PostMapping("/sign_in")
    @ResponseBody
    public ResponseEntity<?> authUser(User newUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenGenerator.generateJwtToken(authentication);
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            String username = loggedInUser.getName();

            return new ResponseEntity<>(new JWTResponse(jwt, username), HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AJAXResponse(false, "Введенные логин или пароль неверны!"), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
