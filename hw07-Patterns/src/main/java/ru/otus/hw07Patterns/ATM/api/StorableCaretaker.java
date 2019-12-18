package ru.otus.hw07Patterns.ATM.api;

import ru.otus.hw07Patterns.ATM.storeATMstate.Memento;

public interface StorableCaretaker {
    public Memento getMemento();
    public void restore();
}
