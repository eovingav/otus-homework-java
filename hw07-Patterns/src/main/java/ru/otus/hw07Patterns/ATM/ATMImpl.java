package ru.otus.hw07Patterns.ATM;

import ru.otus.hw07Patterns.ATM.api.*;
import ru.otus.hw07Patterns.ATM.storeATMstate.Memento;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartmentAbsctract;
import ru.otus.hw07Patterns.ATMDepartment.api.IerarchicalComponent;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.io.*;
import java.util.*;

public class ATMImpl extends ATMabstract {

    private String name;
    private Nominals[] usedNominals;
    private MoneyStorage storageCells;
    private int balance = 0;

    public ATMImpl(Nominals[] nominals){
        usedNominals = nominals;
        storageCells = new MoneyStorageImpl(nominals);
    }

    public ATMImpl(Nominals[] nominals, Map<Nominals, Integer> initBundle){
        usedNominals = nominals;
        storageCells = new MoneyStorageImpl(nominals);
        for (Nominals nominal : nominals) {
            if (initBundle.containsKey(nominal)) {
                storageCells.addBanknote(nominal, initBundle.get(nominal));
            }
        }
        balance = storageCells.getSum();
    }

    public void addToStorage(Map<Nominals, Integer> banknotes){

        Set<Nominals> keySet = banknotes.keySet();
        for (Nominals nominal : keySet) {
            storageCells.addBanknote(nominal, banknotes.get(nominal));
        }

        balance = storageCells.getSum();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance(){
        return balance;
    }

    public Integer getBanknotesCountByNominal(Nominals nominal){
        return storageCells.getBanknotesCountByNominal(nominal);
    }

    public void printStorage(){
        storageCells.printStorage();
    }

    public MoneyStorage withdrawFunds(int sum){

        MoneyStorage withdrawResult = checkPossibleWithrdaw(sum);

        Integer minNominal = storageCells.minimumAvailableNominal();
        if ( minNominal > sum){
            throw new RuntimeException("минимальная доступная сумма выдачи " + minNominal);
        }

        if (sum > balance){
            throw new RuntimeException("запрошенная сумма больше доступной, выберите сумму меньше");
        }

        String stringAvailableNominals = storageCells.getAvailableNominals().toString();
        if (withdrawResult.getSum() != sum){
            throw new RuntimeException("невозможно выдать сумму доступными номиналами (" + stringAvailableNominals + ")");
        }
        Map<Nominals, Integer> banknotes = withdrawResult.getMoneyStorage();
        Set<Nominals> keySet = banknotes.keySet();
        for (Nominals nominal : keySet) {
            int withdrawCount = banknotes.get(nominal);
            storageCells.addBanknote(nominal, -withdrawCount);
        }
        balance = storageCells.getSum();
        return withdrawResult;
    }

    private MoneyStorage checkPossibleWithrdaw(int sum) {

        Map<Nominals, Integer> copyStorage = storageCells.getMoneyStorage();

        int[] f = findPossibleBanknotes(sum);
        MoneyStorage withdrawResult = withdrawFromStorage(sum, f, copyStorage);

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

    private MoneyStorage withdrawFromStorage(int sum, int[] f, Map<Nominals, Integer> copyStorage) {

        int[] nomValuesArray = getNominalsValueArray();
        int k = usedNominals.length;
        int sumWithdraw = sum;
        int count = 0;

        MoneyStorage withdrawResult = new MoneyStorageImpl();

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

        return withdrawResult;
    }

    public Memento saveState() {
       byte[] state;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeInt(this.balance);
            out.writeObject(this.storageCells);
            state = baos.toByteArray();
            out.close();
        }catch (Exception e){
            state = new byte[0];
        }

        return new Memento(state);
    }

    public void restoreState(Memento memento) {
        byte[] state = memento.getState();
        ATMImpl object;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(state);
            ObjectInputStream in = null;
            in = new ObjectInputStream(bais);
            this.balance = (int) in.readInt();
            this.storageCells = (MoneyStorageImpl) in.readObject();
            in.close();
        } catch (Exception e) {
            new Exception("Error restoring state");
        }
    }


    public long accept(Visitor visitor) throws Exception {
        return visitor.visitATM(this);
    }

    @Override
    public String toString() {
        return "ATM{" +
                "name='" + name + '\'' +
                '}';
    }

}
