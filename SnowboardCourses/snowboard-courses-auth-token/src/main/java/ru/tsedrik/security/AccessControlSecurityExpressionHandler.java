package ru.tsedrik.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Класс, включающий возможность реализации авторизации для сложной ролевой модели
 */
public final class AccessControlSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final RoleFactory roleFactory;

    private AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();

    public AccessControlSecurityExpressionHandler(RoleFactory roleFactory) {
        this.roleFactory = roleFactory;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication auth, MethodInvocation mi) {
        AccessControlSecurityExpressionRoot accessControlSecurityExpressionRoot =
                new AccessControlSecurityExpressionRoot((TokenAuthentication) auth, this.roleFactory);

        Field field = ReflectionUtils
                .findField(AccessControlSecurityExpressionRoot.class, "target");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, accessControlSecurityExpressionRoot, mi);

        accessControlSecurityExpressionRoot.setTrustResolver(this.trustResolver);
        accessControlSecurityExpressionRoot.setPermissionEvaluator(this.getPermissionEvaluator());
        accessControlSecurityExpressionRoot.setRoleHierarchy(this.getRoleHierarchy());
        return accessControlSecurityExpressionRoot;
    }
}
