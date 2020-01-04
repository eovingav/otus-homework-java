package hw15MessageSystem.db.api.model;


import hw15MessageSystem.db.api.Identifiable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PhoneDataSet> phones;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles;

    private String addressString;

    private String phonesString;

    public User(){
        this.address = new AddressDataSet();
        this.phones = new ArrayList<PhoneDataSet>();
        this.addressString = "";
        this.phonesString = "";
    }

    public User(long id, String name, int age, AddressDataSet address){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address= address;
        this.addressString = address.toString();
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
        this.addressString = address.toString();
    }

    public String getAddressString() {
        return addressString;
    }

    public String getPhonesString() {
        return phonesString;
    }

    public void setPhonesString(String phonesString) {
        this.phonesString = phonesString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public void setPhones(List<PhoneDataSet> phones) {

        this.phones = phones;
        this.phonesString = phonesToString();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", password=" + password +
                ", address=" + address.toString()  +
                ", phones=");
        stringBuilder.append(phonesToString());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private String phonesToString() {
        int phonesCount = phones.size();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (PhoneDataSet phone:phones) {
            stringBuilder.append(phone.toString());
            i++;
            if (i < phonesCount){
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public boolean hasRole(Role role){

        boolean hasRole = false;

        for (Role userRole: roles){
            if (userRole.equals(role)){
                hasRole = true;
            }
        }

        return hasRole;
    }
}
