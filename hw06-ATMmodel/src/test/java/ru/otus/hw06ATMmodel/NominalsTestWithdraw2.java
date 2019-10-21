package ru.otus.hw06ATMmodel;

import ru.otus.hw06ATMmodel.api.Nominals;

public enum NominalsTestWithdraw2 implements Nominals {
    _1(1),
    _7(7),
    _15(15);

    private int value;

    NominalsTestWithdraw2(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
