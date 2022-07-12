package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при существовании канала с представленным UUID.
 *
 * @author Aurelius
 */
public class UUIDTokenAlreadyExistException extends Exception {
    public UUIDTokenAlreadyExistException(String message) {
        super(message);
    }
}
