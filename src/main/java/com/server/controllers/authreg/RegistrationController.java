package com.server.controllers.authreg;

import com.server.entity.User;
import com.server.service.impl.UserServiceImpl;
import com.server.utils.responses.AJAXResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Класс-контроллер отвечающий за регистрацию пользователей.
 * Регистрация пользователя происходит при обращении на URL /sign_up посредством метода addUser(User, BindingResult).
 * Доступ к странице регистрации\авторизации происходит при обращении на URL /authentication посредством метода registration(Model).
 *
 * @see RegistrationController#registration(Model model)
 * @see RegistrationController#addUser(User user, BindingResult bindingResult)
 *
 * @author Aurelius
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Метод-обработчик GET запроса для доступа к странице с авторизацией при обращении на URL /authentication.
     *
     * @param model Интерфейс отвечающий за добавление аттрибутов в модель.
     * @return Страницу authentication.html
     */
    @GetMapping("/authentication")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "authentication";
    }

    /**
     * Метод-обработчик POST запроса для регистрации пользователя при обращении на URL /sign_up.
     *
     * @param user Объект, который хранит информацию о авторизуемом пользователе.
     * @param bindingResult Объект, проверяющая валидность пришедших данных.
     * @return ResponseEntity<AJAXResponse, HTTPStatus.UNAUTHORIZED> при неверных данных или ResponseEntity<>(AJAXResponse, HttpStatus.OK) при верных данных.
     */
    @PostMapping("/sign_up")
    @ResponseBody
    public ResponseEntity<AJAXResponse> addUser(@Valid User user, BindingResult bindingResult) {
        AJAXResponse ajaxResponse = new AJAXResponse(true, "Вы успешно зарегистрировались! Теперь Вы можете авторизоваться!");

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            message.append("Ошибка!");

            for (ObjectError error: bindingResult.getAllErrors()) {
                switch (((FieldError) error).getField()) {
                    case ("username"):
                        message.append("\n\nИмя пользователя не соответствует требованиям (от 6 до 64 символов)!");
                        break;
                    case ("password"):
                        message.append("\n\nПароль не соответствует требованиям (от 8 до 128 символов)!");
                        break;
                }
            }

            ajaxResponse = new AJAXResponse(false, message.toString());
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            ajaxResponse = new AJAXResponse(false, "Ошибка! Пароли не совпадают");
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        }

        if (!userService.addUser(user)) {
            ajaxResponse = new AJAXResponse(false, "Ошибка! Такой пользователь уже существует");
            return new ResponseEntity<>(ajaxResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ajaxResponse, HttpStatus.OK);
    }
}