package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.Person;
import ru.tsedrik.domain.Role;

import java.util.Collection;

/**
 * Интерфейс управления персистентным состоянием объектов типа Person
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Возвращает список записей об участниках с указанной ролью.
     *
     * @param role  роль участника
     * @return  список найденных участников
     */
    Collection<Person> getAllByRole(Role role);

    /**
     * Возвращает участника по его электронному адресу
     *
     * @param email электронный адрес участника
     * @return  найденный участник
     */
    Person getPersonByEmail(String email);

    /**
     * Возвращает участников по их электронному адресу и роли
     * @param email электронный адрес участника
     * @param role  роль участника
     * @return  список найденных участников
     */
    Collection<Person> getAllByEmailAndRole(String email, Role role);
}
