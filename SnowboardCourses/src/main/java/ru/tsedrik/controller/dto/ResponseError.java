package ru.tsedrik.controller.dto;

/**
 * Класс для возврата пользователю информации о возникшем исключении во время обработки его запроса.
 */
public class ResponseError {

    /**
     * Идентификатор объекта
     */
    private Long id;

    /**
     * Сообщение исключения
     */
    private String message;

    /**
     * Код исключения, в результате которого был создан данный объект
     */
    private String errorCode;

    /**
     * Идентификатор системы, в которой произошло исключение
     */
    private String systemId;

    public ResponseError(Long id, String message, String errorCode, String systemId) {
        this.id = id;
        this.message = message;
        this.errorCode = errorCode;
        this.systemId = systemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
