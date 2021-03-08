package ru.tsedrik.security.accesscontrol.role;

import org.springframework.stereotype.Component;
import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import javax.annotation.PostConstruct;

/**
 * Роль, с которой пользователь может выполнять любую операцию над пользователями
 */
@Component(value = UserAdmin.USER_ADMIN)
public class UserAdmin extends AbstractRole{

    public static final String USER_ADMIN = "USER_ADMIN";

    @Override
    @PostConstruct
    public void init() {
        super.init();

        perms.put(BusinessOperation.USER_CREATE.name(), new SimplePermission(BusinessOperation.USER_CREATE));
        perms.put(BusinessOperation.USER_VIEW.name(), new SimplePermission(BusinessOperation.USER_VIEW));
        perms.put(BusinessOperation.USER_DELETE.name(), new SimplePermission(BusinessOperation.USER_DELETE));
        perms.put(BusinessOperation.USER_UPDATE.name(), new SimplePermission(BusinessOperation.USER_UPDATE));
    }

    @Override
    public String getRoleName() {
        return USER_ADMIN;
    }
}
