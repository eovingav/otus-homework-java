package hw15MessageSystem.frontend.controllers;

import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.db.repository.UsersRepository;
import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.frontend.MessageTypes.UserCredetinals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


@Controller
public class UsersController {

    @Autowired
    private final FrontendService frontendService;

    public UsersController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @MessageMapping("/login")
    @SendTo("/topic/loginResult")
    public LoginResult loginResult(UserCredetinals credetinals) throws InterruptedException {
        int counter = 1;
        CountDownLatch waitLatch = new CountDownLatch(counter);
        AtomicReference<LoginResult> result = new AtomicReference<>(new LoginResult(credetinals.getName(), false));
        frontendService.userAuth(credetinals, loginResult -> {
            result.set(loginResult);
            waitLatch.countDown();
        });
        waitLatch.await();
        return result.get();
    }

    @MessageMapping("/getUsers")
    @SendTo("/topic/userList")
    public List<User> userList() throws InterruptedException {
        int counter = 1;
        CountDownLatch waitLatch = new CountDownLatch(counter);
        AtomicReference<List<User>> result = new AtomicReference<>(new ArrayList<User>());
        frontendService.userList(users -> {
            result.set(users);
            waitLatch.countDown();
        });
        waitLatch.await();
        return result.get();
    }

    @MessageMapping("/userAdd")
    @SendTo("/topic/userAdd")
    public Long addUser(User user) throws InterruptedException {
        int counter = 1;
        CountDownLatch waitLatch = new CountDownLatch(counter);
        AtomicLong result = new AtomicLong();
        frontendService.userAdd(user, newUserID -> {
            result.set(newUserID);
            waitLatch.countDown();
        });
        waitLatch.await();
        return result.get();
    }
}
