package hw11WebServer.usersadmin;

import hw11WebServer.api.Identifiable;
import hw11WebServer.api.model.AddressDataSet;
import hw11WebServer.api.model.PhoneDataSet;
import hw11WebServer.api.model.Role;
import hw11WebServer.api.model.User;
import hw11WebServer.hibernate.HibernateExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersAdminService {
    private final HibernateExecutor hibernateExecutor;

    public UsersAdminService(HibernateExecutor hibernateExecutor) {
        this.hibernateExecutor = hibernateExecutor;
    }

    public List<List> getUsers() {

        List<Identifiable> users = hibernateExecutor.getObjectList(User.class);
        List<List> result = new ArrayList<>();
        for (Identifiable user:users){
            List<String> userAsList = ((User) user).getAsList();
            result.add(userAsList);
        }
        return result;
    }

    public long addUser(Map<String, String> parameterValues) {

        String name = parameterValues.get("name");
        int age = Integer.parseInt(parameterValues.get("age"));
        String street = parameterValues.get("address");
        AddressDataSet address = new AddressDataSet(0,street);
        User user = new User(0, name, age, address );
        String[] phones = parameterValues.get("phones").split(",");
        List<PhoneDataSet> userPhones = new ArrayList<>();
        for (String phone: phones){
            userPhones.add(new PhoneDataSet(0, phone, user));
        }
        user.setPhones(userPhones);
        user.setPassword(parameterValues.get("password"));
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
}
