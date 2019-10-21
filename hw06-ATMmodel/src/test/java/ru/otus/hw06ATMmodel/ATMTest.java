package ru.otus.hw06ATMmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw06ATMmodel.api.ATM;
import ru.otus.hw06ATMmodel.api.MoneyStorage;
import ru.otus.hw06ATMmodel.api.Nominals;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    @Test
    @DisplayName("Test put money (init ATM)")
    void initATMTest() throws Exception {
        Integer bankontesInCell = 20;
        Nominals[] nominals = NominalsRUB.values();
        Map<Nominals, Integer> initBundle = initBundle(nominals, bankontesInCell);
        int initBalance = getBalanceBundle(initBundle);

        ATM atm = new ATMImpl(nominals, initBundle);
        for (Nominals nominal : nominals) {
            Integer currentValue = atm.getBanknotesCountByNominal(nominal);
            assertEquals(bankontesInCell, currentValue);
        }
        assertEquals(initBalance, atm.getBalance());
        atm.printStorage();
    }

    @Test
    @DisplayName("Test put money (add to ATM)")
    void addToStorageTest() throws Exception {
        Integer bankontesInCell = 20;
        Nominals[] nominals = NominalsRUB.values();
        Map<Nominals, Integer> initBundle = initBundle(NominalsRUB.values(), bankontesInCell);
        int initBalance = getBalanceBundle(initBundle);

        ATM atm = new ATMImpl(nominals, initBundle);
        atm.addToStorage(initBundle);
        bankontesInCell = bankontesInCell * 2;
        for (Nominals nominal: nominals){
            Integer bankontesInCellATM = atm.getBanknotesCountByNominal(nominal);
            assertEquals(bankontesInCell, bankontesInCellATM);
        }
        assertEquals(initBalance * 2, atm.getBalance());
        atm.printStorage();
    }

    @Test
    @DisplayName("Withdraw money (test 1)")
    void withdrawTest1(){
        Nominals[] nominals = NominalsTestWithdraw1.values();
        Integer banknotesCount = 10;
        Map<Nominals, Integer> initBundle = initBundle(nominals, banknotesCount);
        ATM atm = new ATMImpl(nominals, initBundle);
        Integer withdraw = 120;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        MoneyStorage banknotes = atm.withdrawFunds(withdraw);
        banknotes.printStorage();
        assertEquals((int) banknotes.getSum(),(int) withdraw );
    }

    @Test
    @DisplayName("Withdraw money (test 2)")
    void withdrawTest2(){
        Nominals[] nominals = NominalsTestWithdraw2.values();
        Integer banknotesCount = 10;
        Map<Nominals, Integer> initBundle = initBundle(nominals, banknotesCount);
        ATM atm = new ATMImpl(nominals, initBundle);
        Integer withdraw = 21;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        MoneyStorage banknotes = atm.withdrawFunds(withdraw);
        banknotes.printStorage();
        assertEquals((int) banknotes.getSum(), (int) withdraw);
    }

    @Test
    @DisplayName("Withdraw money (test 3)")
    void withdrawTest3(){
        Nominals[] nominals = NominalsTestWithdraw2.values();
        Integer banknotesCount = 1;
        Map<Nominals, Integer> initBundle = initBundle(nominals, banknotesCount);
        ATM atm = new ATMImpl(nominals, initBundle);
        Integer withdraw = 19;
        assertThrows(RuntimeException.class, () -> {
            atm.withdrawFunds(withdraw);
            }, "невозможно выдать сумму");
    }

    @Test
    @DisplayName("Withdraw money (test 4)")
    void withdrawTest4(){
        Nominals[] nominals = NominalsTestWithdraw3.values();
        Integer banknotesCount = 1;
        Map<Nominals, Integer> initBundle = initBundle(nominals, banknotesCount);
        ATM atm = new ATMImpl(nominals, initBundle);
        Integer withdraw = 19;
        assertThrows(RuntimeException.class, () -> {
            atm.withdrawFunds(withdraw);
        }, "невозможно выдать сумму");
    }

    @Test
    @DisplayName("Withdraw money (test 5)")
    void withdrawTest5(){
        Nominals[] nominals = NominalsTestWithdraw3.values();
        Integer banknotesCount = 10;
        Map<Nominals, Integer> initBundle = initBundle(nominals, banknotesCount);
        ATM atm = new ATMImpl(nominals, initBundle);
        Integer withdraw = 19;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        MoneyStorage banknotes = atm.withdrawFunds(withdraw);
        banknotes.printStorage();
        assertEquals((int) banknotes.getSum(), (int) withdraw);
    }

    private Map<Nominals, Integer> initBundle(Nominals[] nominals, int count){
        Map<Nominals, Integer> initBundle = new HashMap<Nominals, Integer>();
        for (Nominals nominal : nominals) {
            initBundle.put(nominal, count);
        }
        return initBundle;
    }

    private int getBalanceBundle(Map<Nominals, Integer> initBundle){
        int sum = 0;
        Set<Nominals> keys = initBundle.keySet();
        for (Nominals nominal : keys) {
            sum = sum + nominal.getValue() * initBundle.get(nominal);
        }
        return sum;
    }
}