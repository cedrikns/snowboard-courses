package ru.tsedrik.service;

import ru.tsedrik.controller.dto.PersonDto;
import ru.tsedrik.controller.dto.PersonSearchDto;
import ru.tsedrik.model.Person;

import java.util.List;

/**
 * PersonService представляет интерфейс взаимодействия с классом Person
 */
public interface PersonService {

    /**
     * Добавляет нового участника курсов.
     *
     * @param personDto    новый участник, который будет добавлен
     */
    PersonDto addPerson(PersonDto personDto);

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
    boolean deletePersonById(Long id);

    /**
     * Запрашивает участника курса по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого участника
     * @return  найденный участник
     */
    PersonDto getPersonById(Long id);

    /**
     * Обновляет участника курсов.
     *
     * @param personDto    участник, который будет обновлен
     */
    PersonDto updatePerson(PersonDto personDto);

    /**
     * Получает всех участников, соответствующих параметрам поиска
     *
     * @param personSearchDto  параметры для поиска
     * @return      список найденных участников
     */
    List<PersonDto> getAllPerson(PersonSearchDto personSearchDto);
}
