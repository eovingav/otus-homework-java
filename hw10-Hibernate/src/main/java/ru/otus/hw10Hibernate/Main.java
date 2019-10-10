package ru.otus.hw10Hibernate;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10Hibernate.api.Identifiable;
import ru.otus.hw10Hibernate.hibernate.HibernateUtils;
import ru.otus.hw10Hibernate.hibernate.sessionmanager.HibernateExecutor;
import ru.otus.hw10Hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw10Hibernate.model.User;

import java.util.Optional;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        HibernateExecutor hibernateExecutor = new HibernateExecutor(sessionManager);

        User user = new User(0, "ИвановИИ", 25);
        long userID = hibernateExecutor.saveObject(user);

        Optional<Identifiable> mayBeCreatedUser = hibernateExecutor.getObject(userID, User.class);
        //logger.info(mayBeCreatedUser.get().toString());


    }
}
