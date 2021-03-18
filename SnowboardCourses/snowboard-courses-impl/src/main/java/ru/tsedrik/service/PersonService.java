package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.domain.Person;
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
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_CREATE, \"Вы не можете создавать участника курса\")")
    Mono<PersonDto> addPerson(PersonDto personDto);

    /**
     * Удаляет существующего участника курса.
     *
     * @param person    существующий участник курса, который будет удален
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_DELETE, \"Вы не можете удалять участника курса\")")
    Mono<Boolean> deletePerson(Person person);

    /**
     * Удаляет существующего участника курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого участника
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_DELETE, \"Вы не можете удалять участника курса\")")
    Mono<Boolean> deletePersonById(Long id);

    /**
     * Запрашивает участника курса по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого участника
     * @return  найденный участник
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_VIEW, \"Вы не можете просматривать участника курса\")")
    Mono<PersonDto> getPersonById(Long id);

    /**
     * Обновляет участника курсов.
     *
     * @param personDto    участник, который будет обновлен
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_UPDATE, \"Вы не можете изменять участника курса\")")
    Mono<PersonDto> updatePerson(PersonDto personDto);

    /**
     * Получает всех участников, соответствующих наболу полей из personSearchDto
     *
     * @param personSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных участников, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).PERSON_VIEW, \"Вы не можете просматривать участников курса\")")
    Flux<PersonDto> getPersons(PersonSearchDto personSearchDto, Pageable pageable);
}
