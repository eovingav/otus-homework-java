package hw15MessageSystem.frontend.handlers;

import hw15MessageSystem.common.Serializers;
import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.frontend.MessageTypes.LoginResult;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserListResponseHandler  implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserListResponseHandler.class);

    private final FrontendService frontendService;

    public UserListResponseHandler(FrontendService frontendService) {

        this.frontendService = frontendService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            List<User> userData = Serializers.deserialize(msg.getPayload(), List.class);
            UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
            frontendService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(userData));

        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
