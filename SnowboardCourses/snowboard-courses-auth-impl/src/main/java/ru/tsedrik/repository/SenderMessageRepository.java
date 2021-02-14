package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.domain.SenderMessage;

import java.util.UUID;

/**
 * Интерфейс управления персистентным состоянием объектов типа SenderMessage
 */
@Repository
public interface SenderMessageRepository extends JpaRepository<SenderMessage, UUID>, JpaSpecificationExecutor<SenderMessage> {

    @Modifying
    @Query("update SenderMessage sm set sm.status = :status where sm.id = :id")
    int updateMessageStatusForId(@Param("status") MessageStatus status, @Param("id") UUID id);

    void deleteAllByStatus(MessageStatus status);

}
