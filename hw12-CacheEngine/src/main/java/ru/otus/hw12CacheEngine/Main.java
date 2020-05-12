package ru.otus.hw12CacheEngine;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12CacheEngine.api.DBService;
import ru.otus.hw12CacheEngine.api.sessionmanager.SessionManager;
import ru.otus.hw12CacheEngine.hibernate.HibernateExecutor;
import ru.otus.hw12CacheEngine.hibernate.HibernateUtils;
import ru.otus.hw12CacheEngine.model.AddressDataSet;
import ru.otus.hw12CacheEngine.model.PhoneDataSet;
import ru.otus.hw12CacheEngine.model.User;
import ru.otus.hw12CacheEngine.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12CacheEngine.api.Identifiable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static String hibernateCfgFileName = "hibernate.cfg.xml";
    private static String loadDataSqlFileName = "hw12-CacheEngine/src/main/resources/dataset.sql";

    public static void main(String[] args) throws IOException {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory( hibernateCfgFileName,
                AddressDataSet.class, PhoneDataSet.class, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        DBService hibernateExecutor = new HibernateExecutor(sessionManager);
        loadData(hibernateExecutor);

        // первое чтение из базы данных
        long countReadObjects = 100;
        ArrayList<User>  usersFirstRead = readData(countReadObjects, hibernateExecutor);

        // повторное чтение без кэша
        ArrayList<User>  usersSecondRead = readData(countReadObjects, hibernateExecutor);

        boolean useCache = true;
        hibernateExecutor.setUseCache(useCache);

        // первое чтение из базы данных
        ArrayList<User>  usersFirstReadCached = readData(countReadObjects, hibernateExecutor);

        // повторное чтение закэшированных данных
        ArrayList<User> usersSecondReadCached = readData(countReadObjects, hibernateExecutor);

        /* TODO: проверить удаление кэша при очистке WeakHashMap

        // чтение закэшированных данных после очистки
        ArrayList<User> usersThirdReadCached = readData(countReadObjects, hibernateExecutor);

         */

    }

    private static void outputEntityOptional(String header, Optional<Identifiable> mayBeEntity) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeEntity.ifPresentOrElse(System.out::println, () -> logger.info("Entity not found"));
    }

    private static void loadData(DBService hibernateExecutor) throws IOException {
        StringBuilder sb = new StringBuilder();
        Files.lines(Paths.get(loadDataSqlFileName), StandardCharsets.UTF_8).forEach(sb::append);
        String sqlText = sb.toString();
        hibernateExecutor.executeUpdate(sqlText);
    }

    private static ArrayList<User> readData(long countReadObjects, DBService hibernateExecutor) {
        long startTime = System.currentTimeMillis();
        logger.info("начало чтения:" + startTime);
        ArrayList<User> userList = new ArrayList<>();
        for (long i = 1; i <= countReadObjects; i++) {
            userList.add((User) hibernateExecutor.getObject(i, User.class).get());
        }
        long endTime = System.currentTimeMillis();
        logger.info("окончание чтения:" + endTime);
        long duration = endTime - startTime;
        logger.info("затрачено времени:" + duration);
        return userList;
    }

}
