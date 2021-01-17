package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для возврата пользователю информации о возникшем исключении во время обработки его запроса.
 */
@ApiModel(description = "Модель для ответа в результате ошибки")
public class ResponseError {

    /**
     * Идентификатор объекта
     */
    @ApiModelProperty(value = "Идентификатор объекта", example = "1608061807886", required = true)
    private Long id;

    /**
     * Сообщение исключения
     */
    @ApiModelProperty(value = "Сообщение исключения", example = "Что-то пошло не так", required = true)
    private String message;

    /**
     * Код исключения, в результате которого был создан данный объект
     */
    @ApiModelProperty(value = "Код исключения", example = "illegalArgumentException", required = true)
    private String errorCode;

    /**
     * Идентификатор системы, в которой произошло исключение
     */
    @ApiModelProperty(value = "Идентификатор системы", example = "snowboard-courses", required = true)
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
