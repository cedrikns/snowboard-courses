package ru.tsedrik.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.User;
import ru.tsedrik.domain.Role;
import ru.tsedrik.domain.UserStatus;
import ru.tsedrik.repository.UserRepository;
import ru.tsedrik.resource.dto.PageDto;
import ru.tsedrik.resource.dto.UserDto;
import ru.tsedrik.resource.dto.UserSearchDto;
import ru.tsedrik.resource.dto.UserWithPasswordDto;
import ru.tsedrik.validator.UserDtoValidator;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса UserService
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * Объект для управления персистентным состоянием объектов типа User
     */
    private UserRepository userRepository;

    /**
     * Объект для маппинга между классами User и UserDto
     */
    private MapperFacade mapperFacade;

    /**
     * Объект для валидации данных, пришедших с контроллера в UserDto
     */
    private UserDtoValidator userDtoValidator;


    public UserServiceImpl(UserRepository userRepository, MapperFacade mapperFacade, UserDtoValidator userDtoValidator){
        this.userRepository = userRepository;
        this.mapperFacade = mapperFacade;
        this.userDtoValidator = userDtoValidator;
    }

    @Override
    public UserDto addUser(UserWithPasswordDto userWithPasswordDto) {

        User user = mapperFacade.map(userWithPasswordDto, User.class);

        user.setId(System.currentTimeMillis());
        if (user.getRole() == null){
            user.setRole(Role.ROLE_USER);
        }
        user.setStatus(UserStatus.ACTIVED);

        userRepository.save(user);

        UserDto userDto = mapperFacade.map(user, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserDto userDto = userRepository.findById(id)
                .map(user -> mapperFacade.map(user, UserDto.class))
                .orElseThrow(() -> new IllegalArgumentException("There wasn't found user with id = " + id));

        return userDto;
    }

    @Override
    public boolean deleteUserById(Long id) {
        userRepository.updateUserSetStatusForId(UserStatus.DELETED, id);
        return true;
    }

    @Override
    public UserDto updateUser(UserWithPasswordDto userWithPasswordDto) {
        Long id = userWithPasswordDto.getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There wasn't found user with id = " + id));

        if (!user.getUserName().equals(userWithPasswordDto.getUserName())){
            throw new IllegalArgumentException("Изменять имя пользователя запрещено.");
        }

        mapperFacade.map(userWithPasswordDto, user);
        userRepository.save(user);

        UserDto userDto = mapperFacade.map(user, UserDto.class);

        return userDto;
    }

    @Override
    public PageDto<UserDto> getUsers(UserSearchDto userSearchDto, Pageable pageable) {

        Page<User> page = userRepository.findAll(getSpecification(userSearchDto), pageable);

        List<UserDto> users = page
                .map(user -> mapperFacade.map(user, UserDto.class))
                .toList();

        return new PageDto<>(users, page.getTotalElements());
    }

    private Specification<User> getSpecification(UserSearchDto userSearchDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userSearchDto.getRole() != null) {
                predicates.add(root.get("role").in(Role.valueOf(userSearchDto.getRole().toUpperCase())));
            }

            if (userSearchDto.getEmail() != null) {
                predicates.add(root.get("email").in(userSearchDto.getEmail()));
            }

            if (userSearchDto.getUserName() != null){
                predicates.add(root.get("userName").in(userSearchDto.getUserName()));
            }

            if (userSearchDto.getStatus() != null){
                predicates.add(root.get("status").in(userSearchDto.getStatus()));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
