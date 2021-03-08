package ru.tsedrik.security.accesscontrol.role;

import org.springframework.stereotype.Component;
import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import javax.annotation.PostConstruct;

/**
 * Роль, с которой пользователь может просматривать сущности системы
 */
@Component(value = Viewer.VIEWER)
public class Viewer extends AbstractRole {

    public static final String VIEWER = "VIEWER";

    @Override
    @PostConstruct
    public void init() {
        super.init();

        perms.put(BusinessOperation.USER_VIEW.name(), new SimplePermission(BusinessOperation.USER_VIEW));
        perms.put(BusinessOperation.COURSE_VIEW.name(), new SimplePermission(BusinessOperation.COURSE_VIEW));
        perms.put(BusinessOperation.LOCATION_VIEW.name(), new SimplePermission(BusinessOperation.LOCATION_VIEW));
        perms.put(BusinessOperation.PERSON_VIEW.name(), new SimplePermission(BusinessOperation.PERSON_VIEW));
    }

    @Override
    public String getRoleName() {
        return VIEWER;
    }
}
