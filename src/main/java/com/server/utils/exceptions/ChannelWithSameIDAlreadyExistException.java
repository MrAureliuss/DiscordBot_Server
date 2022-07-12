package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при существовании канала с представленным ID.
 *
 * @author Aurelius
 */
public class ChannelWithSameIDAlreadyExistException extends Exception {
    public ChannelWithSameIDAlreadyExistException(String message) {
        super(message);
    }
}
