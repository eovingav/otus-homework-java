package hw15MessageSystem.frontend;


import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.frontend.MessageTypes.UserCredetinals;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
  void userAuth(UserCredetinals credetinals, Consumer<LoginResult> dataConsumer);
  void userList(Consumer<List<User>> dataConsumer);
  void userAdd(User user, Consumer<Long> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

