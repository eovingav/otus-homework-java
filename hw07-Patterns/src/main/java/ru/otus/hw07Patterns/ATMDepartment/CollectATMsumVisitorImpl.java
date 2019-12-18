package ru.otus.hw07Patterns.ATMDepartment;

import ru.otus.hw07Patterns.ATM.api.ATMabstract;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartmentAbsctract;
import ru.otus.hw07Patterns.ATMDepartment.api.IerarchicalComponent;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.util.Set;

public class CollectATMsumVisitorImpl implements Visitor {

    public long getBalance(ATMDepartmentAbsctract... args) throws Exception {
        long sum = 0;

        for (ATMDepartmentAbsctract department:args){
            sum = sum + department.accept(this);
        }
        return sum;
    }

    @Override
    public long visitATM(ATMabstract atm) {
        return atm.getBalance();
    }

    @Override
    public long visitATMDepartment(ATMDepartmentAbsctract atmDepartment) throws Exception {
        long sum = 0;
        Set<IerarchicalComponent> children = atmDepartment.getChildren();
        for(IerarchicalComponent child: children){
            sum = sum + child.accept(this);
        }
        return sum;
    }
}
