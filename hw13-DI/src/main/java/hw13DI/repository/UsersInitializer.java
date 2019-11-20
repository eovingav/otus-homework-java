package hw13DI.repository;

import hw13DI.api.model.AddressDataSet;
import hw13DI.api.model.PhoneDataSet;
import hw13DI.api.model.Role;
import hw13DI.api.model.User;
import hw13DI.hibernate.HibernateExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersInitializer {

    @Autowired
    private final HibernateExecutor hibernateExecutor;

    public UsersInitializer(HibernateExecutor hibernateExecutor) {
        this.hibernateExecutor = hibernateExecutor;
        initializeData();
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
