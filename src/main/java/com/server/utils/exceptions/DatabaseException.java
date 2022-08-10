package com.server.utils.exceptions;

/**
 * Класс для пробрасывания Checked-ошибки возникающей при работе с базой данных.
 *
 * @author Aurelius
 */
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}
