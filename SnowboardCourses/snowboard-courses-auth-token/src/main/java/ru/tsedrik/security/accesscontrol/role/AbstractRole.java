package ru.tsedrik.security.accesscontrol.role;

import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Общий класс родитель для всех ролей
 */
public abstract class AbstractRole implements Role{

    protected Map<String, SimplePermission> perms = new ConcurrentHashMap<>();

    public void init() {
    }

    @Override
    public Map<String, SimplePermission> getPermissions() {
        return perms;
    }

    @Override
    public Set<BusinessOperation> getOperations() {
        return perms.values()
                .stream()
                .map(simplePermission -> simplePermission.getBusinessOperation())
                .collect(Collectors.toSet());
    }
}
