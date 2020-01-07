package ru.otus.hw07Patterns.ATM.nominals;

import ru.otus.hw07Patterns.ATM.api.Nominals;

public enum NominalsRUB implements Nominals {
        _100(100),
        _200(200),
        _500(500),
        _1000(1000),
        _5000(5000);

        private int value;

       NominalsRUB(int value) {
                this.value = value;
        }

        public int getValue(){
                return this.value;
        }

}