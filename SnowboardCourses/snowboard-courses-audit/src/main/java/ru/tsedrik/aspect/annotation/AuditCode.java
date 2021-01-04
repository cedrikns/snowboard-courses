package ru.tsedrik.aspect.annotation;

/**
 * Справочник действий, производящихся в системе
 */
public enum AuditCode {
    PERSON_CREATE, PERSON_UPDATE, PERSON_DELETE,
    COURSE_CREATE, COURSE_UPDATE, COURSE_DELETE,
    LOCATION_CREATE, LOCATION_UPDATE, LOCATION_DELETE
}
