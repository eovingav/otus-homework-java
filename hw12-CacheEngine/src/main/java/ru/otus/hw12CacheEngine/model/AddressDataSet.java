package ru.otus.hw12CacheEngine.model;

import ru.otus.hw12CacheEngine.api.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(long id, String street) {
        this.id = id;
        this.street = street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return street;
    }
}
