package ru.tsedrik.service;

import org.springframework.data.domain.Pageable;
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
    UserDto addUser(UserWithPasswordDto userWithPasswordDto);

    /**
     * Удаляет существующего пользователя по его идентификатору.
     *
     * @param id    идентификатор пользователя
     * @return  успешно ли прошло удаление
     */
    boolean deleteUserById(Long id);

    /**
     * Запрашивает пользователя по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого пользователя
     * @return  найденный пользователь
     */
    UserDto getUserById(Long id);

    /**
     * Обновляет пользователя.
     *
     * @param userWithPasswordDto    пользователь, который будет обновлен
     */
    UserDto updateUser(UserWithPasswordDto userWithPasswordDto);

    /**
     * Получает всех пользователей, соответствующих наболу полей из userSearchDto
     *
     * @param userSearchDto набор параметров, по которым осуществляется поиск
     * @param pageable  настройка отображения результата
     * @return  страница найденных пользователей, соотвествующая настройкам объекта pageable
     */
    PageDto<UserDto> getUsers(UserSearchDto userSearchDto, Pageable pageable);
}
