package ru.otus.hw07Patterns;

import ru.otus.hw07Patterns.ATM.ATMImpl;
import ru.otus.hw07Patterns.ATM.api.ATM;
import ru.otus.hw07Patterns.ATM.api.ATMabstract;
import ru.otus.hw07Patterns.ATM.api.Nominals;
import ru.otus.hw07Patterns.ATM.nominals.NominalsRUB;
import ru.otus.hw07Patterns.ATMDepartment.ATMDepartmentImpl;
import ru.otus.hw07Patterns.ATMDepartment.CollectATMsumVisitorImpl;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartment;
import ru.otus.hw07Patterns.ATMDepartment.api.ATMDepartmentAbsctract;
import ru.otus.hw07Patterns.ATMDepartment.api.Visitor;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Integer bankontesInCell = 20;
        Nominals[] nominals = NominalsRUB.values();
        Map<Nominals, Integer> initBundle = initBundle(nominals, bankontesInCell);
        ATMabstract atm1 =  new ATMImpl(nominals, initBundle);
        atm1.setName("atm1");
        atm1.printStorage();

        bankontesInCell = 10;
        initBundle = initBundle(nominals, bankontesInCell);
        ATMabstract atm2 =  new ATMImpl(nominals, initBundle);
        atm2.setName("atm2");
        atm2.printStorage();

        bankontesInCell = 15;
        initBundle = initBundle(nominals, bankontesInCell);
        ATMabstract atm3 =  new ATMImpl(nominals, initBundle);
        atm3.setName("atm3");
        atm3.printStorage();

        ATMDepartmentAbsctract atmDepartment1 = new ATMDepartmentImpl("ATM department Russia");
        ATMDepartmentAbsctract atmDepartment2 = new ATMDepartmentImpl("ATM department Deutschland");
        ATMDepartmentAbsctract atmDepartment3 = new ATMDepartmentImpl("ATM department main");
        atmDepartment1.registerChild(atm1);
        atmDepartment2.registerChild(atm2);
        atmDepartment2.registerChild(atm3);
        atmDepartment3.registerChild(atmDepartment1);
        atmDepartment3.registerChild(atmDepartment2);

        CollectATMsumVisitorImpl sumCollector = new CollectATMsumVisitorImpl();

        System.out.println("Total sum in all ATMs is " + sumCollector.getBalance(atmDepartment3));

        atm1.withdrawFunds(200);
        atm1.printStorage();
        atm2.withdrawFunds(400);
        atm2.printStorage();
        atm3.withdrawFunds(1000);
        atm3.printStorage();
        System.out.println("Total sum in all ATMs is " + sumCollector.getBalance(atmDepartment3));

        atmDepartment1.restoreATM(atm1);
        System.out.println("Total sum in all ATMs is " + sumCollector.getBalance(atmDepartment3));

        atmDepartment3.restoreATMs();
        System.out.println("Total sum in all ATMs is " + sumCollector.getBalance(atmDepartment3));;

    }

    private static Map<Nominals, Integer> initBundle(Nominals[] nominals, int count){
        Map<Nominals, Integer> initBundle = new HashMap<Nominals, Integer>();
        for (Nominals nominal : nominals) {
            initBundle.put(nominal, count);
        }
        return initBundle;
    }
}
