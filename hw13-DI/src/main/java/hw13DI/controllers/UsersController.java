package hw13DI.controllers;

import hw13DI.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import hw13DI.api.model.User;

import javax.annotation.Resource;


@Controller
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    UsersRepository repository;

    @GetMapping("/")
    public String userLogin(@NotNull Model model) {
        model.addAttribute("user", new User());
        return "index.html";
    }

    @GetMapping("/users")
    public String userListView(@NotNull Model model) {
        model.addAttribute("userAdd", new User());
        model.addAttribute("users", repository.getUsers());
        return "users.html";
    }

    @PostMapping("/users/add")
    public RedirectView userAdd(@NotNull @ModelAttribute("userAdd") User user) {
        long id = repository.addUser(user);
        return new RedirectView("/users", true);
    }

    @PostMapping("/users")
    public RedirectView userLogin(@NotNull @ModelAttribute("user") User user) {
        if (repository.authenticate(user.getName(), user.getPassword())
                && repository.userHasRole(user.getName(), "admin")){
            return new RedirectView("/users", true);
        }else {
            return new RedirectView("/", true);
        }
    }

}