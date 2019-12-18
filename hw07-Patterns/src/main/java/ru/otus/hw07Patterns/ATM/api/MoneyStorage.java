package ru.otus.hw07Patterns.api;

import java.util.ArrayList;
import java.util.Map;

public interface MoneyStorage {
    public void printStorage();
    public Integer getBanknotesCountByNominal(Nominals nominal);
    public void addBanknote(Nominals nominal, int count);
    public Map<Nominals, Integer> getMoneyStorage();
    public int getSum();
    public Integer minimumAvailableNominal();
    public ArrayList<Integer> getAvailableNominals();

}
