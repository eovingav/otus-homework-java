package hw15MessageSystem.db.handlers;

import hw15MessageSystem.common.Serializers;
import hw15MessageSystem.db.repository.UsersRepository;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.frontend.MessageTypes.UserCredetinals;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.MessageType;
import hw15MessageSystem.messagesystem.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.Optional;

public class UserAuthRequestHandler implements RequestHandler {

  private final UsersRepository usersRepository;

  public UserAuthRequestHandler(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    UserCredetinals credetinals = Serializers.deserialize(msg.getPayload(), UserCredetinals.class);
    boolean userLoggedIn = usersRepository.authenticate(credetinals.getName(), credetinals.getPassword())
            && usersRepository.userHasRole(credetinals.getName(), "admin");
    LoginResult data = new LoginResult(credetinals.getName(), userLoggedIn);
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_AUTH.getValue(), Serializers.serialize(data)));
  }
}
