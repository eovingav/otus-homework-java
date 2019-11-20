package ru.otus.hw06ATMmodel;

import ru.otus.hw06ATMmodel.api.Nominals;

public enum NominalsTestWithdraw3 implements Nominals {
    _3(3),
    _7(7),
    _15(15);

    private int value;

    NominalsTestWithdraw3(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}