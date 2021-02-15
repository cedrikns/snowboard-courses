package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.ReceiverMessage;

import java.util.UUID;

/**
 * Интерфейс управления персистентным состоянием объектов типа SenderMessage
 */
@Repository
public interface ReceiverMessageRepository extends JpaRepository<ReceiverMessage, UUID>, JpaSpecificationExecutor<ReceiverMessage> {

}
