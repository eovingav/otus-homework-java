package hw15MessageSystem.db.handlers;

import hw15MessageSystem.common.Serializers;
import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.db.repository.UsersRepository;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.frontend.MessageTypes.UserCredetinals;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.MessageType;
import hw15MessageSystem.messagesystem.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserListRequestHandler  implements RequestHandler {

    @Autowired
    private final UsersRepository usersRepository;

    public UserListRequestHandler(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<User> data = usersRepository.getUsers();
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_LIST.getValue(), Serializers.serialize(data)));
    }
}
