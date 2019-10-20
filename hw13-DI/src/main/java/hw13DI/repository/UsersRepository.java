package hw13DI.repository;

import hw13DI.api.model.User;

import java.util.List;

public interface UsersRepository {
    public List<User> getUsers();
    public long addUser(User user);
    public Boolean authenticate(String name, String password);
    public Boolean userHasRole(String userName, String roleName);
}
