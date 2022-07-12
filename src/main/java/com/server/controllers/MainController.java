package com.server.controllers;

import com.server.entity.Channel;
import com.server.entity.User;
import com.server.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * Класс-контроллер отвечающий за доступ клиента к главной странице.
 *
 * @see MainController#registration(Model)
 *
 * @author Aurelius
 */
@Controller
public class MainController {
    @Autowired
    ChannelRepository channelRepository;

    /**
     * Метод-обработчик GET запроса к главной странице.
     * При запросе к главной странице метод добавляет в атрибут списки каналов юзера.
     *
     * @param model model Интерфейс отвечающий за добавление аттрибутов в модель.
     * @return Возвращает страницу main.html
     */
    @GetMapping("/")
    public String registration(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Channel> channels = channelRepository.getChannelsByOwner(user);

        model.addAttribute("channels", channels);
        return "main";
    }
}
