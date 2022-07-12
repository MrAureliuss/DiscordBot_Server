package com.server.utils.responses;

/**
 * Класс для отправки ответа на клиентскую часть.
 * На клиенте данный класс обрабатывается при помощи AJAX.
 *
 * @author Aurelius
 */
public class AJAXResponse {
    private boolean validated;
    private String message;

    public AJAXResponse(boolean validated) {
        this.validated = validated;
    }

    public AJAXResponse(boolean validated, String message) {
        this.validated = validated;
        this.message = message;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
