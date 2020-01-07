package ru.otus.hw07Patterns.ATM.storeATMstate;

import ru.otus.hw07Patterns.ATM.api.Storable;
import ru.otus.hw07Patterns.ATM.api.StorableCaretaker;

public class StorableCaretakerImpl implements StorableCaretaker {

  private Storable atm;
  private Memento memento;

  public StorableCaretakerImpl(Storable atm) {
    this.atm = atm;
    this.memento = atm.saveState();
  }

  public Memento getMemento() {
    return memento;
  }

  public void restore(){
    atm.restoreState(memento);
  }
}
