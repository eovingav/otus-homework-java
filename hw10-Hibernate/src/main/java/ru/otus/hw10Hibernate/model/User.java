package ru.otus.hw10Hibernate.model;

import ru.otus.hw10Hibernate.api.Identifiable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet adress;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_id")
    private List<PhoneDataSet> phones;

    public User(){

    }
    public User(long id, String name, int age, AddressDataSet address, List<PhoneDataSet> phones){
        this.id = id;
        this.name = name;
        this.age = age;
        this.adress = address;
        this.phones = phones;
    }

    @Override
    public long getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void addAge(int age){
        this.age = this.age + age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + adress.toString()  +
                ", phones=");
        int phonesCount = phones.size();
        int i = 0;
        for (PhoneDataSet phone:phones) {
            stringBuilder.append(phone.toString());
            i++;
            if (i < phonesCount){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }


}
