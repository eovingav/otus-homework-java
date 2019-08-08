package ru.otus.hw06ATMmodel;

import java.util.*;

public class ATM {

    private Nominals[] usedNominals;
    private Map<Nominals, Integer> storageCells = new HashMap<Nominals, Integer>();
    private int balance = 0;

    public ATM(Nominals[] nominals){
        usedNominals = nominals;
        initStorageCells();
    }

    private void initStorageCells() {
        for (Nominals nominal : usedNominals) {
            storageCells.put(nominal, 0);
        }
    }

    public ATM(Nominals[] nominals, Map<Nominals, Integer> initBundle){
        usedNominals = nominals;
        initStorageCells();
        for (Nominals nominal : storageCells.keySet()) {
            if (! Objects.isNull(initBundle.get(nominal))){
                storageCells.put(nominal, initBundle.get(nominal));
            }
        }
        balance = getSumStorage(storageCells);
    }

    public int getSumStorage(Map<Nominals, Integer> storage) {
        int sumStorage = 0;
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys) {
            sumStorage = sumStorage + nominal.getValue() * storage.get(nominal);
        }
        return sumStorage;
    }

    public void addToStorage(Map<Nominals, Integer> banknotes){

        Set<Nominals> keySet = banknotes.keySet();
        for (Nominals nominal : keySet) {
            Integer currentCountNominal = storageCells.get(nominal);
            storageCells.put(nominal, currentCountNominal + banknotes.get(nominal));
        }

        balance = getSumStorage(storageCells);
    }

    int getBalance(){
        return balance;
    }

    public Map<Nominals, Integer> withdrawFunds(int sum){

        WithdrawResult withdrawResult = checkPossibleWithrdaw(sum);

        if (!withdrawResult.getSuccess()){
            throw new RuntimeException("невозможно выдать сумму");
        }
        Map<Nominals, Integer> banknotes = withdrawResult.getWithdrawBanknotes();
        Set<Nominals> keySet = banknotes.keySet();
        for (Nominals nominal : keySet) {
            int currentCount = storageCells.get(nominal);
            int withdrawCount = banknotes.get(nominal);
            currentCount = currentCount - withdrawCount;
            storageCells.put(nominal, currentCount);
        }
        balance = getSumStorage(storageCells);
        return banknotes;
    }

    private WithdrawResult checkPossibleWithrdaw(int sum) {

        Map<Nominals, Integer> copyStorage = new HashMap<Nominals, Integer>();
        for (Nominals nominal:usedNominals){
            copyStorage.put(nominal, storageCells.get(nominal));
        }

        int[] f = findPossibleBanknotes(sum);
        WithdrawResult withdrawResult = withdrawFromStorage(sum, f, copyStorage);

        return withdrawResult;
    }

    private int[] findPossibleBanknotes(int sum) {
        int INF=Integer.MAX_VALUE - 1; // Значение константы }бесконечность}
        int[] f = new int[sum+1];
        f[0]=0;
        int[] nomValuesArray = getNominalsValueArray();

        int k = usedNominals.length;
        for(int m = 1; m <= sum; ++m)
        {
            f[m]=INF;
            for(int i=0; i < k; ++i)
            {
                if ( m >= nomValuesArray[i]  && f[m-nomValuesArray[i] ]+1<f[m])
                    f[m] = f[m-nomValuesArray[i]]+1;
            }
        }
        return f;
    }

    private int[] getNominalsValueArray() {
        int[] nomValuesArray = new int[usedNominals.length];
        for (int i = 0; i < usedNominals.length; i++){
             nomValuesArray[i] = usedNominals[i].getValue();
         }
        return nomValuesArray;
    }


    void printStorage(Map<Nominals, Integer> storage){
        Set<Nominals> keys = storage.keySet();
        for (Nominals nominal : keys) {
            System.out.println("nominal " + nominal.getValue() + " count " + storage.get(nominal));
        }
        System.out.println("balance: " + balance);
    }

    Map<Nominals, Integer> getStorage(){
        return storageCells;
    }

    private WithdrawResult withdrawFromStorage(int sum, int[] f, Map<Nominals, Integer> copyStorage) {

        int[] nomValuesArray = getNominalsValueArray();
        int k = usedNominals.length;
        int sumWithdraw = sum;
        int count = 0;

        WithdrawResult withdrawResult = new WithdrawResult();

        while (sumWithdraw > 0 && count <= sum) {
            count++;
            for (int i = 0; i < k; ++i) {
                if (sumWithdraw >= nomValuesArray[i] && f[sumWithdraw - nomValuesArray[i]] == f[sumWithdraw] - 1) {
                    int currentCountNominal = copyStorage.get(usedNominals[i]);
                    if (currentCountNominal >= 1) {
                        sumWithdraw = sumWithdraw - nomValuesArray[i];
                        copyStorage.put(usedNominals[i], currentCountNominal - 1);
                        withdrawResult.addBanknote(usedNominals[i], 1);
                        break;
                    }
                }
            }
        }

        withdrawResult.success(sum);
        return withdrawResult;
    }

    private class WithdrawResult{

        private boolean success;
        private Map<Nominals, Integer> withdrawBanknotes;

        WithdrawResult(){
            withdrawBanknotes = new HashMap<Nominals, Integer>();
        }

        private void addBanknote(Nominals nominal, int count){
            Integer currentCount = withdrawBanknotes.get(nominal);
            if (Objects.isNull(currentCount)){
                currentCount = count;
            }else {
                currentCount = currentCount + count;
            }
            withdrawBanknotes.put(nominal, currentCount);
        }

        private Map<Nominals, Integer> getWithdrawBanknotes(){
            return withdrawBanknotes;
        }

        private void success(int sum){
            int currentSum = ATM.this.getSumStorage(withdrawBanknotes);
            success = currentSum == sum;
        }

        private boolean getSuccess(){
            return success;
        }
    }
}
