package hw15MessageSystem.frontend;

import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.frontend.MessageTypes.UserCredetinals;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.MessageType;
import hw15MessageSystem.messagesystem.MsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  private final String databaseServiceClientName;

  public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void userAuth(UserCredetinals credetinals, Consumer<LoginResult> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, credetinals, MessageType.USER_AUTH);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

    @Override
    public void userList(Consumer<List<User>> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, null, MessageType.USER_LIST);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void userAdd(User user, Consumer<Long> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, user, MessageType.USER_ADD);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }
}
