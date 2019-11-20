package hw11WebServer.auth;

import hw11WebServer.api.Identifiable;
import hw11WebServer.api.model.Role;
import hw11WebServer.hibernate.HibernateExecutor;
import hw11WebServer.api.model.User;

import java.util.Optional;

public class UserLoginService {
    private final HibernateExecutor executor;

    public UserLoginService(HibernateExecutor executor) {
        this.executor = executor;
    }

    public boolean authenticate(String name, String password) {

        Optional<Identifiable> result = executor.findByField(name, "name", User.class);
        if (result.isPresent()) {
            User user = (User) result.get();
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }

    public boolean userHasRole(String userName, String roleName){
        Optional<Identifiable> userOptional = executor.findByField(userName, "name", User.class);
        Optional<Identifiable> roleOptional = executor.findByField(roleName, "name", Role.class);
        if (userOptional.isPresent()&&roleOptional.isPresent()) {
            User user = (User) userOptional.get();
            Role role = (Role) roleOptional.get();
            return user.hasRole(role);
        }else {
            return false;
        }
    }
}
