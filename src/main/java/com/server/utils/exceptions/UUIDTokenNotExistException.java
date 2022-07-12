package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при несуществовании канала с представленным UUID.
 *
 * @author Aurelius
 */
public class UUIDTokenNotExistException extends Exception {
    public UUIDTokenNotExistException(String message) {
        super(message);
    }
}
