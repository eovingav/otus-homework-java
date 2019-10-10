package ru.otus.hw10Hibernate.model;

import ru.otus.hw10Hibernate.api.Identifiable;

import javax.persistence.*;

public class PhoneDataSet implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private String number;

    @Override
    public long getID() {
        return this.id;
    }
}
