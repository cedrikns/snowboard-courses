package ru.tsedrik.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Данные о текущем пользвоателе с токеном
 */
public class TokenAuthentication implements Authentication {

    /**
     * Токен текущего пользователя
     */
    private String token;

    /**
     * Признак аутентификации
     */
    private boolean authenticated;

    /**
     * Информация о текущем пользователе
     */
    private UserDetails principal;

    private List<String> role;

    public TokenAuthentication(String token, UserDetails principal, List<String> role) {
        this.token = token;
        this.principal = principal;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        if (principal != null) {
            return principal.getUsername();
        } else {
            return null;
        }
    }

    public String getToken() {
        return token;
    }

    public List<String> getRole() {
        return role;
    }
}
