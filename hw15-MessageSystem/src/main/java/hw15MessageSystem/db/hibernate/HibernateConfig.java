package hw15MessageSystem.db.hibernate;

import hw15MessageSystem.db.api.model.AddressDataSet;
import hw15MessageSystem.db.api.model.PhoneDataSet;
import hw15MessageSystem.db.api.model.Role;
import hw15MessageSystem.db.api.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@RequiredArgsConstructor
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(){
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                AddressDataSet.class, PhoneDataSet.class, Role.class, User.class);
    }
}
