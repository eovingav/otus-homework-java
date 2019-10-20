package hw13DI.repository;

import hw13DI.api.Identifiable;
import hw13DI.api.model.AddressDataSet;
import hw13DI.api.model.PhoneDataSet;
import hw13DI.api.model.Role;
import hw13DI.api.model.User;
import hw13DI.hibernate.HibernateExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private final HibernateExecutor hibernateExecutor;

    public UsersRepositoryImpl(HibernateExecutor hibernateExecutor) {

        this.hibernateExecutor = hibernateExecutor;
    }

    @PostConstruct
    public final void init() {
        initializeData();
    }

    @Override
    public List<User> getUsers() {

        List<Identifiable> users = hibernateExecutor.getObjectList(User.class);
        List<User> result = new ArrayList<>();
        for (Identifiable user:users){
            result.add((User) user);
        }
        return result;
    }

    @Override
    public long addUser(User user) {

        AddressDataSet address = new AddressDataSet(0,user.getAddressString());
        user.setAddress(address);
        String[] phones = user.getPhonesString().split(",");
        List<PhoneDataSet> userPhones = new ArrayList<>();
        for (String phone: phones){
            userPhones.add(new PhoneDataSet(0, phone, user));
        }
        user.setPhones(userPhones);
        long id = hibernateExecutor.saveObject(user);
        Optional<Identifiable> userRoleOptional = hibernateExecutor.findByField("user", "name", Role.class);
        if (userRoleOptional.isPresent()){
            Role userRole = (Role)userRoleOptional.get();
            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            hibernateExecutor.saveObject(user);
        }
        return id;
    }

    public Boolean authenticate(String name, String password) {

        Optional<Identifiable> result = hibernateExecutor.findByField(name, "name", User.class);
        if (result.isPresent()) {
            User user = (User) result.get();
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }

    public Boolean userHasRole(String userName, String roleName){
        Optional<Identifiable> userOptional = hibernateExecutor.findByField(userName, "name", User.class);
        Optional<Identifiable> roleOptional = hibernateExecutor.findByField(roleName, "name", Role.class);
        if (userOptional.isPresent()&&roleOptional.isPresent()) {
            User user = (User) userOptional.get();
            Role role = (Role) roleOptional.get();
            return user.hasRole(role);
        }else {
            return false;
        }
    }

    private void initializeData(){

        Role roleAdmin = new Role(0, "admin");
        List<Role> admins = new ArrayList<>();
        admins.add(roleAdmin);

        Role roleUser = new Role(0, "user");
        List<Role> users = new ArrayList<>();
        users.add(roleUser);

        AddressDataSet address = new AddressDataSet(0, "Ленина");
        User user = new User(0, "ИвановИИ", 25, address);
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(0, "+74012359925", user));
        phones.add(new PhoneDataSet(0, "+79216123456", user));
        user.setPhones(phones);
        user.setPassword("123987456");
        user.setRoles(admins);
        long userID = hibernateExecutor.saveObject(user);

        AddressDataSet address2 = new AddressDataSet(0,"Кирова");
        User user2 = new User(0, "ПетровАН", 32, address2);
        List<PhoneDataSet> phones2 = new ArrayList<>();
        phones2.add(new PhoneDataSet(0, "+74012111222", user2));
        phones2.add(new PhoneDataSet(0, "+79219874563", user2));
        user.setPassword("159753");
        user2.setPhones(phones2);
        user2.setRoles(users);
        userID = hibernateExecutor.saveObject(user2);

    }
}
