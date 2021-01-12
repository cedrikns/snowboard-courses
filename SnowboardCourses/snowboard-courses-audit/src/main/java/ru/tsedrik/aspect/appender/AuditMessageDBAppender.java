package ru.tsedrik.aspect.appender;

import ch.qos.logback.classic.spi.*;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.db.ConnectionSource;
import ch.qos.logback.core.db.DBHelper;
import ru.tsedrik.aspect.AspectService;
import ru.tsedrik.aspect.AuditMessage;

import java.sql.*;

/**
 * Класс для логирования данных аудита в БД
 */
public class AuditMessageDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {


    /**
     * Запрос для вставки данных сообщения AuditMessage в БД
     */
    protected final String insertSQL = "INSERT INTO audit_message " +
            "(id, audit_сode, event_status, start_time, end_time, user_name, params, return_value) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Подключение к БД
     */
    protected ConnectionSource connectionSource;


    @Override
    public void start() {

        if (connectionSource == null) {
            throw new IllegalStateException("DBAppender cannot function without a connection source");
        }

        super.start();
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void append(ILoggingEvent eventObject) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        try {
            connection = connectionSource.getConnection();
            connection.setAutoCommit(false);

            AuditMessage auditMessage = AspectService.deserializeAuditMessageFromString(eventObject.getMessage());

            insertStatement = connection.prepareStatement(insertSQL);

            int counter = 0;
            insertStatement.setObject(++counter, auditMessage.getUuid());
            insertStatement.setString(++counter, auditMessage.getAuditCode().name());
            insertStatement.setString(++counter, auditMessage.getAuditMessageEvent().name());

            Timestamp startTime = null;
            if (auditMessage.getStartTime() != null){
                startTime = Timestamp.valueOf(auditMessage.getStartTime());
            }

            insertStatement.setTimestamp(++counter, startTime);

            Timestamp endTime = null;
            if (auditMessage.getStartTime() != null){
                endTime = Timestamp.valueOf(auditMessage.getStartTime());
            }
            insertStatement.setTimestamp(++counter, endTime);
            insertStatement.setString(++counter, auditMessage.getUserName());
            insertStatement.setString(++counter, auditMessage.getParams());
            insertStatement.setString(++counter, auditMessage.getResult());

            int updateCount = insertStatement.executeUpdate();
            if (updateCount != 1) {
                addWarn("Failed to insert loggingEvent");
            }

            connection.commit();
        } catch (Throwable sqle) {
            addError("Problem appending event", sqle);
        } finally {
            DBHelper.closeStatement(insertStatement);
            DBHelper.closeConnection(connection);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
