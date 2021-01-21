package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Класс для возврата пользователю информации об ошибках, которые возникли во время валидации формы для создания сущности User
 */
@ApiModel(description = "Модель для ответа в результате ошибок при валидации формы User")
public class UserFormValidationError {

    /**
     * Идентификатор объекта
     */
    @ApiModelProperty(value = "Идентификатор объекта", example = "1608061807885", required = true)
    private Long id;

    /**
     * Код исключения, в результате которого был создан данный объект
     */
    @ApiModelProperty(value = "Код исключения, в результате которого был создан данный объект", example = "userFormValidationException", required = true)
    private String errorCode;

    /**
     * Списко ошибок, которые возникли в результате валидации заполнения полей формы
     */
    @ApiModelProperty(value = "Списко ошибок, которые возникли в результате валидации заполнения полей формы", required = true)
    private List<ObjectError> errors;

    /**
     * Идентификатор системы, в которой произошло исключение
     */
    @ApiModelProperty(value = "Идентификатор системы, в которой произошло исключение", example = "snowboard-courses", required = true)
    private String systemId;

    public UserFormValidationError(Long id, List<ObjectError> errors, String errorCode, String systemId) {
        this.id = id;
        this.errors = errors;
        this.errorCode = errorCode;
        this.systemId = systemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
