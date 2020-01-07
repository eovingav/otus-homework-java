package ru.otus.hw07Patterns.ATM.api;

import ru.otus.hw07Patterns.ATM.storeATMstate.Memento;

public interface Storable {
    public Memento saveState();
    public void restoreState(Memento memento);
}
