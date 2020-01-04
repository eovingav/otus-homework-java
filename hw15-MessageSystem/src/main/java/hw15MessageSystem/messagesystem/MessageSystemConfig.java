package hw15MessageSystem.messagesystem;

import hw15MessageSystem.db.handlers.UserAuthRequestHandler;
import hw15MessageSystem.db.repository.UsersRepository;
import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.frontend.FrontendServiceImpl;
import hw15MessageSystem.frontend.handlers.UserAuthResponseHandler;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan
public class MessageSystemConfig {

    @Autowired
    private final MessageSystem messageSystem;

    @Autowired
    private final UsersRepository usersRepository;

    private final String DATABASE_SERVICE_CLIENT_NAME = "db";
    private final String FRONTEND_SERVICE_CLIENT_NAME = "frontend";

    public MessageSystemConfig(MessageSystem messageSystem, UsersRepository usersRepository) {
        this.messageSystem = messageSystem;
        this.usersRepository = usersRepository;
    }

    @Bean
    public FrontendService frontendService(){
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        databaseMsClient.addHandler(MessageType.USER_AUTH, new UserAuthRequestHandler(usersRepository));
        messageSystem.addClient(databaseMsClient);

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(MessageType.USER_AUTH, new UserAuthResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);

        return frontendService;
    }

}
