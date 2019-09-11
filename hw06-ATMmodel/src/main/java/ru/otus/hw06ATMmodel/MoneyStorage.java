package ru.otus.hw06ATMmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class MoneyStorage{

    private Map<Nominals, Integer> storage;
    private int sum;

    MoneyStorage(){
        storage = new HashMap<Nominals, Integer>();
        sum = 0;
    }

    MoneyStorage(Nominals[] nominals){
        storage = new HashMap<Nominals, Integer>();
        for (Nominals nominal : nominals){
            storage.put(nominal, 0);
        }
        sum = 0;
    }

    public void addBanknote(Nominals nominal, int count){
        Integer currentCount = storage.get(nominal);
        if (Objects.isNull(currentCount)){
            currentCount = count;
        }else {
            currentCount = currentCount + count;
        }
        storage.put(nominal, currentCount);
        updateSum();
    }

    public Map<Nominals, Integer> getMoneyStorage(){
        return storage;
    }

    public int getSum() {
        return sum;
    }

    public void printStorage(){
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys) {
            System.out.println("nominal " + nominal.getValue() + " count " + storage.get(nominal));
        }
        System.out.println("balance: " + sum);
    }

    private void updateSum(){
        sum = 0;
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys) {
            sum = sum + nominal.getValue() * storage.get(nominal);
        }
    }
}