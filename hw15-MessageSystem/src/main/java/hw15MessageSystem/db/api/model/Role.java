package hw15MessageSystem.db.api.model;

import hw15MessageSystem.db.api.Identifiable;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
