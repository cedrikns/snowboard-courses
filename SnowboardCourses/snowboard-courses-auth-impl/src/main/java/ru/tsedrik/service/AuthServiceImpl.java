package ru.tsedrik.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.User;
import ru.tsedrik.domain.UserStatus;
import ru.tsedrik.repository.UserRepository;
import ru.tsedrik.resource.dto.AuthUserDto;
import ru.tsedrik.resource.dto.TokenDto;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;

/**
 * Реализация интерфейса AuthService
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    public static final String USER_NOT_FOUND = "Неверный логин или пароль.";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.application.name}")
    private String systemName;

    @Value("${spring.application.token.key}")
    private String secretKey;

    @Value("${spring.application.token.lifetime}")
    private String lifetime;

    private final MapperFacade mapperFacade;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MapperFacade mapperFacade) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public TokenDto createToken(AuthUserDto authUserDto) {
        User user = userRepository.findUserByUserNameAndStatusIsNot(authUserDto.getUserName(), UserStatus.DELETED)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(authUserDto.getPassword(), user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException(USER_NOT_FOUND);
        }

        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());

        String jws = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(user.getUserName())
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(lifetime)))
                .setAudience(authUserDto.getSystemName())
                .setIssuedAt(new Date())
                .setId(randomUUID().toString())
                .setIssuer(systemName)
                .addClaims(claims)
                .signWith(key).compact();

        return new TokenDto(jws);
    }
}
