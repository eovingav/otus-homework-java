package hw11WebServer.api.model;

import hw11WebServer.api.Identifiable;

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

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number, User user) {
        this.id = id;
        this.number = number;
        this.user = user;
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
