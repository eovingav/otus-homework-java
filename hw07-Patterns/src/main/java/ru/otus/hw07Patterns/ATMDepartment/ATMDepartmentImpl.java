package ru.otus.hw07Patterns.ATMDepartment;

import ru.otus.hw07Patterns.ATM.api.ATM;
import ru.otus.hw07Patterns.ATM.api.ATMabstract;
import ru.otus.hw07Patterns.ATM.api.StorableCaretaker;
import ru.otus.hw07Patterns.ATM.storeATMstate.StorableCaretakerImpl;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartmentAbsctract;
import ru.otus.hw07Patterns.ATMDepartment.api.IerarchicalComponent;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.util.*;

public class ATMDepartmentImpl extends ATMDepartmentAbsctract {

    private Map<ATM, StorableCaretaker> initATMstates;
    private String name;
    private Set<IerarchicalComponent> children;
    private Set<ATMabstract> atmSet;

    public ATMDepartmentImpl() {
        children = new HashSet<>();
        atmSet = new HashSet<>();
        initATMstates = new HashMap<>();
        name = "";
    }

    public ATMDepartmentImpl(String name) {
        children = new HashSet<>();
        atmSet = new HashSet<>();
        initATMstates = new HashMap<>();
        this.name = name;
    }


    public Set<IerarchicalComponent> getChildren() {
        Set<IerarchicalComponent> childrens = new HashSet<>();
        for(IerarchicalComponent child: children){
            childrens.add(child);
        }
        return childrens;
    }
    public void registerChild(IerarchicalComponent child){
        children.add(child);
        if (child instanceof ATMabstract) {
            ATMabstract atm = (ATMabstract) child;
            StorableCaretaker caretaker = new StorableCaretakerImpl(atm);
            initATMstates.put(atm, caretaker);
            System.out.println("ATM " + atm.toString() + " registered at " + name);
            atmSet.add(atm);
        }
    }

    @Override
    public long accept(Visitor visitor) throws Exception {
        return visitor.visitATMDepartment(this);
    }

    @Override
    public void restoreATMs() throws Exception {
        RestoreATMVisitorImpl visitor = new RestoreATMVisitorImpl(this);
        visitor.restoreATMs(getChildren());
    }

    public void restoreATM(ATMDepartmentAbsctract atmDepartment) throws Exception {
        atmDepartment.restoreATMs();
    }

    @Override
    public void restoreATM(ATMabstract atm) throws Exception {
        if (atmSet.contains(atm)){
            StorableCaretaker caretaker = initATMstates.get(atm);
            caretaker.restore();
            System.out.println("Restored " + atm);
            atm.printStorage();
        }else{
            throw new Exception("No such ATM in this ATM department!");
        }
    }

}
