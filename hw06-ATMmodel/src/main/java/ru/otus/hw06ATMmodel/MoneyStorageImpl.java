package ru.otus.hw06ATMmodel;

import ru.otus.hw06ATMmodel.api.MoneyStorage;
import ru.otus.hw06ATMmodel.api.Nominals;

import java.util.*;

class MoneyStorageImpl implements MoneyStorage {

    private Map<Nominals, Integer> storage;
    private int sum;

    MoneyStorageImpl(){
        storage = new HashMap<Nominals, Integer>();
        sum = 0;
    }

    MoneyStorageImpl(Nominals[] nominals){
        storage = new HashMap<Nominals, Integer>();
        for (Nominals nominal : nominals){
            storage.put(nominal, 0);
        }
        sum = 0;
    }

    public void addBanknote(Nominals nominal, int count){
        Integer currentCount = Objects.isNull(storage.get(nominal)) ? 0 : storage.get(nominal);
        if ((currentCount + count) < 0) {
            throw new  RuntimeException("ошибка добавления банкноты в ячейку");
        }
        if (Objects.isNull(currentCount)){
            currentCount = count;
        }else {
            currentCount = currentCount + count;
        }

        storage.put(nominal, currentCount);
        updateSum();
    }

    public Integer getBanknotesCountByNominal(Nominals nominal){
        return storage.get(nominal);
    }

    public Map<Nominals, Integer> getMoneyStorage(){

        Map<Nominals, Integer> copyStorage = new HashMap<>();
        Set<Nominals> nominals = storage.keySet();
        for (Nominals nominal:nominals) {
            copyStorage.put(nominal, storage.get(nominal));
        }
        return copyStorage;
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

    public Integer minimumAvailableNominal(){

        if (sum == 0) {
            return 0;
        }

        Set<Nominals> keys = storage.keySet();
        Integer minNominal = Integer.MIN_VALUE;

        for (Nominals nominal : keys){
            if (storage.get(nominal) > 0 && nominal.getValue() < minNominal ){
                minNominal = nominal.getValue();
            }
        }
        return minNominal;
    }

    public ArrayList<Integer> getAvailableNominals(){
        ArrayList<Integer> availableNominals = new ArrayList<>();
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys){
            if (storage.get(nominal) > 0){
                availableNominals.add(nominal.getValue());
            }
        }
        return availableNominals;
    }


    private void updateSum(){
        sum = 0;
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys) {
            sum = sum + nominal.getValue() * storage.get(nominal);
        }
    }
}