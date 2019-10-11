package ru.otus.hw10Hibernate.model;

import ru.otus.hw10Hibernate.api.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return number;
    }
}
