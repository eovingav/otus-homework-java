package hw15MessageSystem.frontend.handlers;

import hw15MessageSystem.common.Serializers;
import hw15MessageSystem.db.api.model.User;
import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.messagesystem.Message;
import hw15MessageSystem.messagesystem.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserAddResponseHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserAddResponseHandler.class);

    private final FrontendService frontendService;

    public UserAddResponseHandler(FrontendService frontendService) {

        this.frontendService = frontendService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            Long userData = Serializers.deserialize(msg.getPayload(), Long.class);
            UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
            frontendService.takeConsumer(sourceMessageId, Long.class).ifPresent(consumer -> consumer.accept(userData));

        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
