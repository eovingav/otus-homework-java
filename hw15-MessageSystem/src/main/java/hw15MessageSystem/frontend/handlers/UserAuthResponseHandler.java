package hw15MessageSystem.frontend.handlers;

import hw15MessageSystem.common.Serializers;
import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserAuthResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(UserAuthResponseHandler.class);

  @Autowired
  private final FrontendService frontendService;

  public UserAuthResponseHandler(FrontendService frontendService) {

    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      LoginResult userData = Serializers.deserialize(msg.getPayload(), LoginResult.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, LoginResult.class).ifPresent(consumer -> consumer.accept(userData));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
