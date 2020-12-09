package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.Group;

/**
 * Интерфейс управления персистентным состоянием объектов типа Group
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
