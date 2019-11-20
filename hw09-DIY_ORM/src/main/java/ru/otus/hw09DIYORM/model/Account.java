package ru.otus.hw09DIYORM.model;

import ru.otus.hw09DIYORM.ORM.ID;

public class Account {

    @ID
    private long no;

    private String type;
    private long rest;

    public Account(long no, String type, long rest){
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public long getRest() {
        return rest;
    }

    public void changeRest(int amount){
        this.rest = this.rest + amount;
    }
    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
