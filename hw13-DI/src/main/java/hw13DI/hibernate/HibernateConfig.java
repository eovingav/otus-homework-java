package hw13DI.hibernate;

import hw13DI.api.model.AddressDataSet;
import hw13DI.api.model.PhoneDataSet;
import hw13DI.api.model.Role;
import hw13DI.api.model.User;
import hw13DI.hibernate.HibernateUtils;
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
