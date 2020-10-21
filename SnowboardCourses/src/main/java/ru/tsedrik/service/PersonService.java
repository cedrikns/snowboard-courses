package ru.tsedrik.service;

import ru.tsedrik.model.Person;

/**
 * PersonService представляет интерфейс взаимодействия с классом Person
 */
public interface PersonService {

    /**
     * Добавляет нового участника курсов.
     *
     * @param person    новый участник, который будет добавлен
     */
    void addPerson(Person person);

    /**
     * Удаляет существующего участника курса.
     *
     * @param person    существующий участник курса, который будет удален
     * @return  удаленный участник
     */
    Person deletePerson(Person person);

    /**
     * Удаляет существующего участника курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого участника
     * @return  удаленный участник
     */
    Person deletePersonById(Long id);

    /**
     * Запрашивает участника курса по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого участника
     * @return  найденный участник
     */
    Person getPersonById(Long id);
}
