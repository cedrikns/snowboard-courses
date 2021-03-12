package ru.tsedrik.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.tsedrik.service.MyClaims;
import ru.tsedrik.service.TokenValidateService;

import java.util.Arrays;
import java.util.Optional;

/**
 * Менеджер, управляющий аутентификацией с помощью токена
 */
@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private TokenValidateService tokenValidateService;

    public TokenAuthenticationManager(TokenValidateService tokenValidateService) {
        this.tokenValidateService = tokenValidateService;
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        String token = Optional.ofNullable(authentication)
                .map(auth -> String.valueOf(auth.getCredentials()))
                .orElse(null);

        if (token == null) {
            authentication.setAuthenticated(false);
            return authentication;
        }

        MyClaims claims = tokenValidateService.parseAndValidate(token);

        UserPrincipal.UserPrincipalBuilder user = UserPrincipal.username(claims.getSubject());
        String[] roles = claims.getRole().split(";");
        user.roles(roles);
        user.email(claims.getEmail());
        UserDetails userDetails = user.build();
        TokenAuthentication tokenAuthentication = new TokenAuthentication(token, userDetails, Arrays.asList(roles));
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;

    }
}
