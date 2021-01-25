package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.tsedrik.domain.Person;
import ru.tsedrik.resource.dto.PageDto;
import ru.tsedrik.resource.dto.PersonDto;
import ru.tsedrik.resource.dto.PersonSearchDto;

/**
 * PersonService представляет интерфейс взаимодействия с классом Person
 */
public interface PersonService {

    /**
     * Добавляет нового участника курсов.
     *
     * @param personDto    новый участник, который будет добавлен
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PersonDto addPerson(PersonDto personDto);

    /**
     * Удаляет существующего участника курса.
     *
     * @param person    существующий участник курса, который будет удален
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deletePerson(Person person);

    /**
     * Удаляет существующего участника курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого участника
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    boolean deletePersonById(Long id);

    /**
     * Запрашивает участника курса по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого участника
     * @return  найденный участник
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PersonDto getPersonById(Long id);

    /**
     * Обновляет участника курсов.
     *
     * @param personDto    участник, который будет обновлен
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PersonDto updatePerson(PersonDto personDto);

    /**
     * Получает всех участников, соответствующих наболу полей из personSearchDto
     *
     * @param personSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных участников, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PageDto<PersonDto> getPersons(PersonSearchDto personSearchDto, Pageable pageable);
}
