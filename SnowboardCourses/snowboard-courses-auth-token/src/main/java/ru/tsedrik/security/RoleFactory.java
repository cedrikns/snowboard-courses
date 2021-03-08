package ru.tsedrik.security;

import org.springframework.stereotype.Component;
import ru.tsedrik.security.accesscontrol.role.Role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Фабрика ролей
 */
@Component
public class RoleFactory {

    private Map<String, Role> roles;

    public RoleFactory() {
        this.roles = new ConcurrentHashMap<>();
    }

    public Map<String, Role> getRoles() {
        return roles;
    }
}
