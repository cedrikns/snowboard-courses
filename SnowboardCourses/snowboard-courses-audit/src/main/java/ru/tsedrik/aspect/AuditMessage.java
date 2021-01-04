package ru.tsedrik.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tsedrik.aspect.annotation.AuditCode;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Представляет собой набор полей сообщения, которое будет логироваться во время аудита
 */
public class AuditMessage {
    /**
     * Идентификатор события аудита.
     */
    private final UUID uuid;

    /**
     * Уникальный код события аудита
     */
    private AuditCode auditCode;

    /**
     * Тип события аудита: возможные значения START, SUCCESS, FAILURE
     */
    private AuditMessageEvent auditMessageEvent;

    /**
     * Время, соответствующее событию аудита START
     */
    private LocalDateTime startTime;

    /**
     * Время, соответствующее событию аудита SUCCESS или FAILURE
     */
    private LocalDateTime endTime;

    /**
     * Имя пользователя, от которого выполняется данный запрос
     */
    private String userName = "";

    /**
     * Входящие параметры запроса
     */
    private Object[] params;

    /**
     * Возвращаемое значение в результате выполнения метода
     */
    private Object result;

    public AuditMessage() {
        this.uuid = UUID.randomUUID();
    }

    public AuditCode getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(AuditCode auditCode) {
        this.auditCode = auditCode;
    }

    public AuditMessageEvent getAuditMessageEvent() {
        return auditMessageEvent;
    }

    public void setAuditMessageEvent(AuditMessageEvent auditMessageEvent) {
        this.auditMessageEvent = auditMessageEvent;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return uuid +
                ";" + auditCode +
                ";" + auditMessageEvent +
                ";" + (startTime == null ? "" : startTime) +
                ";" + (endTime == null ? "" : endTime) +
                ";" + userName +
                ";" + serializeInJson(params) +
                ";" + serializeInJson(result);
    }

    private String serializeInJson(Object o){
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedMessage = "";

        try {
            serializedMessage = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return serializedMessage;
    }
}
