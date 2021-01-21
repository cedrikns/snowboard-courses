package ru.tsedrik.aspect.annotation;

import java.lang.annotation.*;

/**
 * Аннотация для аудита операций, изменяющих данные в системе
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Audit {
    AuditCode value();
}
