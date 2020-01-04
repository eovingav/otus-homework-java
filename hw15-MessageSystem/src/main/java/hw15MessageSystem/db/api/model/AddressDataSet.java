package hw15MessageSystem.db.api.model;

import hw15MessageSystem.db.api.Identifiable;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class AddressDataSet implements Identifiable, Serializable {

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
