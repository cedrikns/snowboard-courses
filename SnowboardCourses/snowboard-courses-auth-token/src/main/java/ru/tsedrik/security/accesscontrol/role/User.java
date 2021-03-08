package ru.tsedrik.security.accesscontrol.role;

import org.springframework.stereotype.Component;
import ru.tsedrik.security.BusinessOperation;
import ru.tsedrik.security.accesscontrol.SimplePermission;

import javax.annotation.PostConstruct;

/**
 * Роль, с которой пользователь может выполнять любую операцию над сущностями, кроме пользователей
 */
@Component(value = User.USER)
public class User extends AbstractRole{

    public static final String USER = "USER";

    @Override
    @PostConstruct
    public void init() {
        super.init();

        perms.put(BusinessOperation.COURSE_CREATE.name(), new SimplePermission(BusinessOperation.COURSE_CREATE));
        perms.put(BusinessOperation.COURSE_VIEW.name(), new SimplePermission(BusinessOperation.COURSE_VIEW));
        perms.put(BusinessOperation.COURSE_DELETE.name(), new SimplePermission(BusinessOperation.COURSE_DELETE));
        perms.put(BusinessOperation.COURSE_UPDATE.name(), new SimplePermission(BusinessOperation.COURSE_UPDATE));
        perms.put(BusinessOperation.COURSE_ENROLL.name(), new SimplePermission(BusinessOperation.COURSE_ENROLL));
        perms.put(BusinessOperation.LOCATION_CREATE.name(), new SimplePermission(BusinessOperation.LOCATION_CREATE));
        perms.put(BusinessOperation.LOCATION_VIEW.name(), new SimplePermission(BusinessOperation.LOCATION_VIEW));
        perms.put(BusinessOperation.LOCATION_DELETE.name(), new SimplePermission(BusinessOperation.LOCATION_DELETE));
        perms.put(BusinessOperation.LOCATION_UPDATE.name(), new SimplePermission(BusinessOperation.LOCATION_UPDATE));
        perms.put(BusinessOperation.PERSON_CREATE.name(), new SimplePermission(BusinessOperation.PERSON_CREATE));
        perms.put(BusinessOperation.PERSON_VIEW.name(), new SimplePermission(BusinessOperation.PERSON_VIEW));
        perms.put(BusinessOperation.PERSON_DELETE.name(), new SimplePermission(BusinessOperation.PERSON_DELETE));
        perms.put(BusinessOperation.PERSON_UPDATE.name(), new SimplePermission(BusinessOperation.PERSON_UPDATE));
    }

    @Override
    public String getRoleName() {
        return USER;
    }
}
