package ru.otus.hw07Patterns.ATMDepartment.api;

import ru.otus.hw07Patterns.ATM.api.ATMabstract;
import ru.otus.hw07Patterns.ATMDepartment.ATMDepartmentImpl;

public interface IerarchicalComponent {
    public long accept(Visitor visitor) throws Exception;
}
