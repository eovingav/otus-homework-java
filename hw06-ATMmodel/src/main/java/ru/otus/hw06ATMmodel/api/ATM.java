package ru.otus.hw06ATMmodel.api;

import ru.otus.hw06ATMmodel.api.MoneyStorage;
import ru.otus.hw06ATMmodel.api.Nominals;

import java.util.Map;

public interface ATM {
    public Integer getBanknotesCountByNominal(Nominals nominal);
    public void printStorage();
    public MoneyStorage withdrawFunds(int sum);
    public int getBalance();
    public void addToStorage(Map<Nominals, Integer> banknotes);
}
