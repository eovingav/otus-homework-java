package ru.otus.hw07Patterns.ATMDepartment;

import ru.otus.hw07Patterns.ATM.api.ATMabstract;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartmentAbsctract;
import ru.otus.hw07Patterns.ATMDepartment.api.IerarchicalComponent;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.util.Collection;

public class RestoreATMVisitorImpl implements Visitor {

    private ATMDepartmentAbsctract atmDepartment;

    public RestoreATMVisitorImpl(ATMDepartmentAbsctract atmDepartment) {
        this.atmDepartment = atmDepartment;
    }

    public void restoreATMs(Collection<IerarchicalComponent> children) throws Exception {

        for (IerarchicalComponent child:children){
            child.accept(this);
        }
    }

    @Override
    public long visitATM(ATMabstract atm) throws Exception {
        atmDepartment.restoreATM(atm);
        return 0;
    }

    @Override
    public long visitATMDepartment(ATMDepartmentAbsctract atmDepartment) throws Exception {
        atmDepartment.restoreATMs();
        return 0;
    }

}
