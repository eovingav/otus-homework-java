package ru.otus.hw06ATMmodel;

public enum NominalsTestWithdraw1 implements Nominals {
    _10(10),
    _60(60),
    _100(100);

    private int value;

    NominalsTestWithdraw1(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

