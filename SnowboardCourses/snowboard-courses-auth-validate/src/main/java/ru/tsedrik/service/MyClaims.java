package ru.tsedrik.service;

import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Map;

/**
 * Состав токена
 */
public class MyClaims extends DefaultClaims {

    private static final String ROLE = "role";
    private static final String EMAIL = "email";

    public MyClaims() {
    }

    public MyClaims(Map<String, Object> map) {
        super(map);
    }

    public String getRole() {
        return get(ROLE, String.class);
    }

    public void setRole(String member) {
        setValue(ROLE, member);
    }

    public String getEmail() {
        return get(EMAIL, String.class);
    }

    public void setEmail(String member) {
        setValue(EMAIL, member);
    }

}
