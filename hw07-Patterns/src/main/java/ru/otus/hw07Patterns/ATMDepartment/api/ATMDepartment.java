package ru.otus.hw07Patterns.ATMDepartment.api;

import ru.otus.hw07Patterns.ATM.api.ATM;
import ru.otus.hw07Patterns.ATM.api.ATMabstract;

import java.util.Set;

public interface ATMDepartment {
    public void restoreATMs() throws Exception;
    public void restoreATM(ATMabstract atm) throws Exception;
    public void registerChild(IerarchicalComponent child);
    public Set<IerarchicalComponent> getChildren();
}
