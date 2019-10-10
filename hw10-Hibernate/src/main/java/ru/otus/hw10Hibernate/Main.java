package ru.otus.hw10Hibernate;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10Hibernate.api.Identifiable;
import ru.otus.hw10Hibernate.hibernate.HibernateUtils;
import ru.otus.hw10Hibernate.hibernate.sessionmanager.HibernateExecutor;
import ru.otus.hw10Hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw10Hibernate.model.AddressDataSet;
import ru.otus.hw10Hibernate.model.PhoneDataSet;
import ru.otus.hw10Hibernate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", AddressDataSet.class, PhoneDataSet.class, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        HibernateExecutor hibernateExecutor = new HibernateExecutor(sessionManager);

        AddressDataSet address = new AddressDataSet(0, "Ленина");
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(0, "+74012359925"));
        phones.add(new PhoneDataSet(0, "+79216123456"));
        User user = new User(0, "ИвановИИ", 25, address, phones);
        long userID = hibernateExecutor.saveObject(user);
        Optional<Identifiable> mayBeCreatedUser1 = hibernateExecutor.getObject(userID, User.class);

        AddressDataSet address2 = new AddressDataSet(0,"Кирова");
        List<PhoneDataSet> phones2 = new ArrayList<>();
        phones2.add(new PhoneDataSet(0, "+74012111222"));
        phones2.add(new PhoneDataSet(0, "+79219874563"));
        User user2 = new User(0, "ПетровАН", 32, address2, phones2);
        userID = hibernateExecutor.saveObject(user2);
        Optional<Identifiable> mayBeCreatedUser2 = hibernateExecutor.getObject(userID, User.class);

        Optional<Identifiable> mayBeCreatedUser3 = hibernateExecutor.getObject(3, User.class);

        outputEntityOptional("created user:", mayBeCreatedUser1);
        outputEntityOptional("created user:", mayBeCreatedUser2);
        outputEntityOptional("created user:", mayBeCreatedUser3);

    }

    private static void outputEntityOptional(String header, Optional<Identifiable> mayBeEntity) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeEntity.ifPresentOrElse(System.out::println, () -> logger.info("Entity not found"));
    }
}
