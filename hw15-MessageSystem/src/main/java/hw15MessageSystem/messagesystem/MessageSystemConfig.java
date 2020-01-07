package hw15MessageSystem.messagesystem;

import hw15MessageSystem.frontend.FrontendService;
import hw15MessageSystem.frontend.FrontendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MessageSystemConfig {

    @Autowired
    private final MessageSystem messageSystem;

    private final String DATABASE_SERVICE_CLIENT_NAME = "db";
    private final String FRONTEND_SERVICE_CLIENT_NAME = "frontend";

    public MessageSystemConfig(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Bean
    @Qualifier("databaseMsClient")
    public MsClient databaseMsClient(){
        return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean
    @Qualifier("frontendMsClient")
    public MsClient frontendMsClient(){
        return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean
    public FrontendService frontendService( @Qualifier("frontendMsClient") MsClient frontendMsClient) {
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }

}
