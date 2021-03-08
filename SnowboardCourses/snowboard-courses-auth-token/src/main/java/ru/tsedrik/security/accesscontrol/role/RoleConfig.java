package ru.tsedrik.security.accesscontrol.role;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.tsedrik.security.RoleFactory;

import java.util.Map;

/**
 * Конфигурационный файл для загрузки всех объектов Role в контекст приложения
 */
@Configuration
public class RoleConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    private RoleFactory roleFactory;

    public RoleConfig(ApplicationContext applicationContext, RoleFactory roleFactory) {
        this.applicationContext = applicationContext;
        this.roleFactory = roleFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Role> roles = applicationContext.getBeansOfType(Role.class);
        if (roles.size() > 0) {
            roleFactory.getRoles().putAll(roles);
        }
    }
}
