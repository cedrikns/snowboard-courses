package ru.tsedrik.security.accesscontrol;

import ru.tsedrik.security.BusinessOperation;

/**
 * Разрешение для роли
 */
public class SimplePermission {

    private BusinessOperation businessOperation;

    public SimplePermission(BusinessOperation businessOperation) {
        this.businessOperation = businessOperation;
    }

    public BusinessOperation getBusinessOperation() {
        return businessOperation;
    }

    public void setBusinessOperation(BusinessOperation businessOperation) {
        this.businessOperation = businessOperation;
    }
}
