package ru.tsedrik.security.accesscontrol.role;

import org.springframework.stereotype.Component;
import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import javax.annotation.PostConstruct;

/**
 * Роль, с которой пользователь может выполнять любую бизнес-операцию
 */
@Component(value = Admin.ADMIN)
public class Admin extends AbstractRole{

    public static final String ADMIN = "ADMIN";

    @Override
    @PostConstruct
    public void init() {
        super.init();

        for (BusinessOperation bo : BusinessOperation.values()){
            perms.put(bo.name(), new SimplePermission(bo));
        }
    }

    @Override
    public String getRoleName() {
        return ADMIN;
    }
}
