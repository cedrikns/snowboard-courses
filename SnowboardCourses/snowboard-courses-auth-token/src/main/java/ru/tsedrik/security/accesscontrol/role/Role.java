package ru.tsedrik.security.accesscontrol.role;

import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import java.util.Map;
import java.util.Set;

/**
 * Интерфейс для работы с ролями
 */
public interface Role {

    /**
     * Возвращает название роли
     */
    String getRoleName();

    /**
     * Возвращает все разрешения текущей роли
     */
    Map<String, SimplePermission> getPermissions();

    /**
     * Возвращает все бизнес-операции, доступные данной роли
     */
    Set<BusinessOperation> getOperations();
}
