package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при существовании канала с представленным дисплейным именем.
 *
 * @author Aurelius
 */
public class ChannelWithSameDisplayNameAlreadyExist extends Exception {
    public ChannelWithSameDisplayNameAlreadyExist(String message) {
        super(message);
    }
}
