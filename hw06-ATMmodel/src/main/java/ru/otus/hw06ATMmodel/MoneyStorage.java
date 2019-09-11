package ru.otus.hw06ATMmodel;

import java.util.*;

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