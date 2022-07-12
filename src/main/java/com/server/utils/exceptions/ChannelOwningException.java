package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при несоответствии юзера и владельца канала.
 *
 * @author Aurelius
 */
public class ChannelOwningException extends Exception {
    public ChannelOwningException(String message) {
        super(message);
    }
}
