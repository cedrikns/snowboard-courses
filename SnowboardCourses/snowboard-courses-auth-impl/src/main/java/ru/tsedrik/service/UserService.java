package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.tsedrik.resource.dto.PageDto;
import ru.tsedrik.resource.dto.UserDto;
import ru.tsedrik.resource.dto.UserSearchDto;
import ru.tsedrik.resource.dto.UserWithPasswordDto;

/**
 * UserService представляет интерфейс взаимодействия с классом User
 */
public interface UserService {

    /**
     * Добавляет нового пользователя.
     *
     * @param userWithPasswordDto    новый пользователь, который будет добавлен
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).USER_CREATE, \"Вы не можете создавать пользователя\")")
    UserDto addUser(UserWithPasswordDto userWithPasswordDto);

    /**
     * Удаляет существующего пользователя по его идентификатору.
     *
     * @param id    идентификатор пользователя
     * @return  успешно ли прошло удаление
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).USER_DELETE, \"Вы не можете удалять пользователя\")")
    boolean deleteUserById(Long id);

    /**
     * Запрашивает пользователя по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого пользователя
     * @return  найденный пользователь
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).USER_VIEW, \"Вы не можете просматривать пользователя\")")
    UserDto getUserById(Long id);

    /**
     * Обновляет пользователя.
     *
     * @param userWithPasswordDto    пользователь, который будет обновлен
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).USER_UPDATE, \"Вы не можете обновлять пользователя\")")
    UserDto updateUser(UserWithPasswordDto userWithPasswordDto);

    /**
     * Получает всех пользователей, соответствующих наболу полей из userSearchDto
     *
     * @param userSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных пользователей, соотвествующая настройкам объекта pageable
     */
    @PreAuthorize("hasPermission(T(ru.tsedrik.security.BusinessOperation).USER_VIEW, \"Вы не можете просматривать пользователей\")")
    PageDto<UserDto> getUsers(UserSearchDto userSearchDto, Pageable pageable);
}
