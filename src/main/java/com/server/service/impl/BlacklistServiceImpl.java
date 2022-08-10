package com.server.service.impl;

import com.server.DTO.BlacklistedUserDTO;
import com.server.DTO.Mappers.BlacklistedUserDTOMapper;
import com.server.entity.BlacklistedUser;
import com.server.entity.Channel;
import com.server.entity.User;
import com.server.repository.BlacklistRepository;
import com.server.repository.ChannelRepository;
import com.server.service.interfaces.BlacklistService;
import com.server.utils.exceptions.ChannelOwningException;
import com.server.utils.exceptions.DatabaseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Класс для добавления пользователей в blacklist канала.
 *
 * @see BlacklistServiceImpl#getBlacklistedUsersByChannelID(String channelID)
 * @see BlacklistServiceImpl#addUserToChannelBlacklist(String jsonBody)
 * @see BlacklistServiceImpl#deleteUserFromBlacklist(String jsonBody)
 *
 * @author Aurelius
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {
    @Autowired
    BlacklistRepository blacklistRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    BlacklistedUserDTOMapper blacklistedUserDTOMapper;

    /**
     * @param channelID Индентификатор канала дискорда, получаемый из команды *authorize в чате дискорда.
     * @return Set<BlacklistedUserDTO> содержащий список пользователей которые находятся в черном списке канала.
     * @throws ChannelOwningException Если канал не принадлежит пользователю.
     */
    @Override
    public Set<BlacklistedUserDTO> getBlacklistedUsersByChannelID(String channelID) throws ChannelOwningException {
        Channel channelFromDB = channelRepository.findChannelByChannelID(channelID);
        if (channelFromDB == null || channelFromDB.getOwner().getID() !=
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getID()) {
            throw new ChannelOwningException("Ошибка! Вы не владелец данного канала!");
        }

        return blacklistRepository.getBlacklistedUserByChannelID(channelID)
                .stream()
                .map(blacklistedUser -> blacklistedUserDTOMapper.mapToBlacklistedUserDTO(blacklistedUser))
                .collect(Collectors.toSet());
    }

    /**
     * @param jsonBody Строка в формате JSON, содержащие параметры для последующего парсинга и работы.
     * @return true при удачном добавлении в черный список канала.
     * @throws ChannelOwningException Если канал не принадлежит пользователю.
     * @throws JSONException Если отсутствует один из требуемых параметров при парсинге JSON.
     * @throws DatabaseException Если пользователь уже добавлен в черный список канала.
     */
    @Override
    @Transactional
    public boolean addUserToChannelBlacklist(String jsonBody) throws ChannelOwningException, JSONException, DatabaseException {
        JSONObject jsonObject = new JSONObject(jsonBody);

        try {
            Channel channelFromDB = channelRepository.findChannelByChannelID(jsonObject.getString("display_name"));
            if (channelFromDB == null || channelFromDB.getOwner().getID() !=
                    ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getID()) {
                throw new ChannelOwningException("Ошибка! Вы не владелец данного канала!");
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            JSONObject reqObject = new JSONObject();
            reqObject.put("display_name", jsonObject.getString("display_name"));
            reqObject.put("user_to_exist", jsonObject.getString("user_to_exist"));
            reqObject.put("channel_owner_id", jsonObject.getString("channel_owner_id"));

            HttpEntity<String> request = new HttpEntity<String>(reqObject.toString(), httpHeaders);

            ResponseEntity<String> responseEntityStr =
                    restTemplate.postForEntity("http://localhost:8000/get_user_id", request, String.class);

            jsonObject = new JSONObject(responseEntityStr.getBody());
            if (blacklistRepository.countBlacklistedUserByUserIDAndChannelID(
                    Long.parseLong(jsonObject.getString("required_user_id")),
                    reqObject.getString("display_name")
            ) >= 1) {
                throw new DatabaseException("Ошибка! Данный пользователь уже находится в черном списке канала!");
            }

            blacklistRepository.save(new BlacklistedUser(
                    reqObject.getString("display_name"),
                    reqObject.getString("user_to_exist"),
                    Long.parseLong(jsonObject.getString("required_user_id")),
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            ));

            return true;
        } catch (JSONException jsonException) {
            throw new JSONException("Ошибка! Отсутствует один из требуемых параметров!");
        }
    }

    /**
     * @param jsonBody Строка в формате JSON, содержащие параметры для последующего парсинга и работы.
     * @return true при удачном удалении из черного списка канала.
     * @throws ChannelOwningException Если канал не принадлежит пользователю.
     * @throws JSONException Если отсутствует один из требуемых параметров при парсинге JSON.
     * @throws DatabaseException Если пользователя не находится в черном списке канала.
     */
    @Override
    @Transactional
    public boolean deleteUserFromBlacklist(String jsonBody) throws ChannelOwningException, JSONException, DatabaseException {
        JSONObject jsonObject = new JSONObject(jsonBody);

        try {
            Channel channelFromDB = channelRepository.findChannelByChannelID(jsonObject.getString("display_name"));
            if (channelFromDB == null || channelFromDB.getOwner().getID() !=
                    ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getID()) {
                throw new ChannelOwningException("Ошибка! Вы не владелец данного канала!");
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            JSONObject reqObject = new JSONObject();
            reqObject.put("display_name", jsonObject.getString("display_name"));
            reqObject.put("user_to_exist", jsonObject.getString("user_to_delete"));
            reqObject.put("channel_owner_id", jsonObject.getString("channel_owner_id"));

            HttpEntity<String> request = new HttpEntity<String>(reqObject.toString(), httpHeaders);

            ResponseEntity<String> responseEntityStr =
                    restTemplate.postForEntity("http://localhost:8000/get_user_id", request, String.class);

            jsonObject = new JSONObject(responseEntityStr.getBody());

            long countOfDeletedUser = blacklistRepository.deleteBlacklistedUserByUserIDAndChannelID(
                    Long.parseLong(jsonObject.getString("required_user_id")),
                    reqObject.getString("display_name"));

            if (countOfDeletedUser == 0) {
                throw new DatabaseException("Ошибка! Данный пользователь не находится в черном списке вашего канала!");
            }

            return true;
        } catch (JSONException jsonException) {
            throw new JSONException("Ошибка! Отсутствует один из требуемых параметров!");
        }
    }
}
