package ru.tsedrik.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import java.util.Optional;

/**
 * Класс для реализации авторизации для сложной ролевой модели
 */
public class AccessControlSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final TokenAuthentication tokenAuthentication;
    private RoleFactory roleFactory;

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public AccessControlSecurityExpressionRoot(TokenAuthentication tokenAuthentication, RoleFactory roleFactory) {
        super(tokenAuthentication);
        this.tokenAuthentication = tokenAuthentication;
        this.roleFactory = roleFactory;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean hasPermission(BusinessOperation permission, String msg) {
        for (String role: tokenAuthentication.getRole()) {
            SimplePermission perm = Optional.ofNullable(roleFactory.getRoles().get(role))
                    .map(r -> r.getPermissions().get(permission.name())).orElse(null);
            if (perm != null) {
                return true;
            }
        }
        throw new AccessDeniedException(msg);
    }
}
