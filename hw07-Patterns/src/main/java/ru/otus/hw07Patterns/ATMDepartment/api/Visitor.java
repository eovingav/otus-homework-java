package ru.otus.hw07Patterns.ATMDepartment.api;

import ru.otus.hw07Patterns.ATM.api.ATMabstract;

public interface Visitor {
    public long visitATM(ATMabstract atm) throws Exception;
    public long visitATMDepartment(ATMDepartmentAbsctract atmDepartment) throws Exception;
}
