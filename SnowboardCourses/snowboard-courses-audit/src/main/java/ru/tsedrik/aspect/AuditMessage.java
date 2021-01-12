package ru.tsedrik.aspect;

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
    private UUID uuid;

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
     * Входящие параметры запроса в формате JSON
     */
    private String params;

    /**
     * Возвращаемое значение в результате выполнения метода в формате JSON
     */
    private String result;

    public AuditMessage() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
