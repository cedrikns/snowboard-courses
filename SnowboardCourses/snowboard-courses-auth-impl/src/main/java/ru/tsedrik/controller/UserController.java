package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.security.TokenAuthentication;
import ru.tsedrik.security.UserPrincipal;
import ru.tsedrik.resource.UserResource;
import ru.tsedrik.resource.dto.*;
import ru.tsedrik.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for User
 */
@RestController
public class UserController implements UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Audit(AuditCode.USER_CREATE)
    @Override
    public ResponseEntity<UserDto> createUser(@RequestBody UserWithPasswordDto userWithPasswordDto, UriComponentsBuilder uriComponentsBuilder){
        logger.debug("createUser with {} - start ", userWithPasswordDto);
        UserDto resultUserDto = userService.addUser(userWithPasswordDto);
        URI uri = uriComponentsBuilder.path("/api/v1/user/" + resultUserDto.getId()).buildAndExpand(resultUserDto).toUri();
        logger.debug("createUser end with result {}", resultUserDto);
        return ResponseEntity.created(uri).body(resultUserDto);
    }

    @Override
    public UserDto getUser(@PathVariable Long id){
        logger.debug("getUser with {} - start ", id);
        UserDto userDto = userService.getUserById(id);
        logger.debug("getUser end with result {}", userDto);
        return userDto;
    }

    @Override
    public PageDto<UserDto> getUsers(@RequestBody UserSearchDto userSearchDto,
                                         @PageableDefault(value = 5) @SortDefault(value = "id") Pageable pageable){
        logger.debug("getUsers with {}, {} - start ", userSearchDto, pageable);
        PageDto<UserDto> result = userService.getUsers(userSearchDto, pageable);
        logger.debug("getUsers end with result {}", result);
        return result;
    }

    @Audit(AuditCode.USER_DELETE)
    @Override
    public boolean deleteUser(@PathVariable Long id){
        logger.debug("deleteUser with {} - start ", id);
        boolean isDeleted = userService.deleteUserById(id);
        logger.debug("deleteUser end with result {}", isDeleted);
        return isDeleted;
    }

    @Audit(AuditCode.USER_UPDATE)
    @Override
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserWithPasswordDto userWithPasswordDto){
        logger.debug("updateUser with {}, {} - start ", id, userWithPasswordDto);
        if (!id.equals(userWithPasswordDto.getId())){
            throw new IllegalArgumentException("Идентификатор в пути запроса " + id + " не совпадает с идентификатором в теле запроса " + userWithPasswordDto.getId());
        }
        UserDto userDto = userService.updateUser(userWithPasswordDto);
        logger.debug("updateUser end with result {}", userDto);
        return userDto;
    }

    @Override
    public ResponseEntity<UserInfoDto> getUserInfo() {
        TokenAuthentication auth = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(a -> a.toString()).collect(Collectors.toList());
        UserInfoDto userInfoDto = new UserInfoDto(
                auth.getName(),
                principal.getEmail(),
                roles);
        return ResponseEntity.ok(userInfoDto);
    }
}
