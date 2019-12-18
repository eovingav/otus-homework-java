package ru.otus.hw07Patterns.ATM;

import ru.otus.hw07Patterns.ATM.api.ATM;
import ru.otus.hw07Patterns.ATM.api.Storable;

public class Caretaker {

  Memento memento;

  public Memento getMemento() {
    return memento;
  }

  public void setMemento(Memento memento) {
    this.memento = memento;
  }
}
