package ru.otus.hw06ATMmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

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

    @Test
    @DisplayName("Test put money (init ATM)")
    void initATMTest() throws Exception {
        Map<Nominals, Integer> initBundle = initBundle(NominalsRUB.values(), 20);
        int initBalance = getBalanceBundle(initBundle);

        ATM atm = new ATM(NominalsRUB.values(), initBundle);
        Map<Nominals, Integer> currentStorage = atm.getStorage();
        Set<Nominals> keys = currentStorage.keySet();
        for (Nominals nominal : keys) {
            Integer initValue = initBundle.get(nominal);
            Integer currentValue = currentStorage.get(nominal);
            assertEquals(initValue, currentValue);
        }
        assertEquals(initBalance, atm.getBalance());
        atm.printStorage(atm.getStorage());
    }

    @Test
    @DisplayName("Test put money (add to ATM)")
    void addToStorageTest() throws Exception {
        Map<Nominals, Integer> initBundle = initBundle(NominalsRUB.values(), 20);
        int initBalance = getBalanceBundle(initBundle);

        ATM atm = new ATM(NominalsRUB.values(), initBundle);
        atm.addToStorage(initBundle);

        assertEquals(initBalance * 2, atm.getBalance());
        Map<Nominals, Integer> currentStorage = atm.getStorage();
        atm.printStorage(atm.getStorage());
    }

    @Test
    @DisplayName("Withdraw money (test 1)")
    void withdrawTest1(){
        Map<Nominals, Integer> initBundle = initBundle(NominalsTestWithdraw1.values(), 10);
        ATM atm = new ATM(NominalsTestWithdraw1.values(), initBundle);
        Integer withdraw = 120;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        Map<Nominals, Integer> banknotes = atm.withdrawFunds(withdraw);
        atm.printStorage(banknotes);
        assertEquals(atm.getSumStorage(banknotes), withdraw );
    }

    @Test
    @DisplayName("Withdraw money (test 2)")
    void withdrawTest2(){
        Map<Nominals, Integer> initBundle = initBundle(NominalsTestWithdraw2.values(), 10);
        ATM atm = new ATM(NominalsTestWithdraw2.values(), initBundle);
        Integer withdraw = 21;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        Map<Nominals, Integer> banknotes = atm.withdrawFunds(withdraw);
        atm.printStorage(banknotes);
        assertEquals(atm.getSumStorage(banknotes), withdraw);
    }

    @Test
    @DisplayName("Withdraw money (test 3)")
    void withdrawTest3(){
        Map<Nominals, Integer> initBundle = initBundle(NominalsTestWithdraw2.values(), 1);
        ATM atm = new ATM(NominalsTestWithdraw2.values(), initBundle);
        Integer withdraw = 19;
        assertThrows(RuntimeException.class, () -> {
            atm.withdrawFunds(withdraw);
            }, "невозможно выдать сумму");
    }

    @Test
    @DisplayName("Withdraw money (test 4)")
    void withdrawTest4(){
        Map<Nominals, Integer> initBundle = initBundle(NominalsTestWithdraw3.values(), 1);
        ATM atm = new ATM(NominalsTestWithdraw3.values(), initBundle);
        Integer withdraw = 19;
        assertThrows(RuntimeException.class, () -> {
            atm.withdrawFunds(withdraw);
        }, "невозможно выдать сумму");
    }

    @Test
    @DisplayName("Withdraw money (test 5)")
    void withdrawTest5(){
        Nominals[] nominals = NominalsTestWithdraw3.values();
        Map<Nominals, Integer> initBundle = initBundle(nominals, 10);
        ATM atm = new ATM(nominals, initBundle);
        Integer withdraw = 19;
        System.out.println("start balance: " + atm.getBalance());
        System.out.println("withdraw: " + withdraw);
        Map<Nominals, Integer> banknotes = atm.withdrawFunds(withdraw);
        atm.printStorage(banknotes);
        assertEquals(atm.getSumStorage(banknotes), withdraw);
    }
}