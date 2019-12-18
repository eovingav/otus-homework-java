package ru.otus.hw07Patterns.ATM.api;

import ru.otus.hw07Patterns.ATMDepartment.api.IerarchicalComponent;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.io.Serializable;
import java.util.Map;

public interface ATM {
    public Integer getBanknotesCountByNominal(Nominals nominal);
    public void printStorage();
    public MoneyStorage withdrawFunds(int sum);
    public int getBalance();
    public void addToStorage(Map<Nominals, Integer> banknotes);
    public void setName(String name);
}
