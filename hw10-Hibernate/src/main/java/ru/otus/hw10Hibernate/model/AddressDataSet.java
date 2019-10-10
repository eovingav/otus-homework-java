package ru.otus.hw10Hibernate.model;

import ru.otus.hw10Hibernate.api.Identifiable;

import javax.persistence.*;

public class AddressDataSet implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;

    @Override
    public long getID() {
        return this.id;
    }
}
