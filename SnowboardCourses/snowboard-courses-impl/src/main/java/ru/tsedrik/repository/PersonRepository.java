package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.Person;


/**
 * Интерфейс управления персистентным состоянием объектов типа Person
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}
