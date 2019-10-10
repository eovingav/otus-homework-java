package ru.otus.hw09DIYORM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09DIYORM.ORM.DbExecutor;
import ru.otus.hw09DIYORM.api.sessionmanager.SessionManager;
import ru.otus.hw09DIYORM.h2.DataSourceH2;
import ru.otus.hw09DIYORM.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.hw09DIYORM.model.Account;
import ru.otus.hw09DIYORM.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private void createTableUser(SessionManager sessionManager) throws SQLException {
        sessionManager.beginSession();
        Connection connection = sessionManager.getCurrentSession().getConnection();
        try (PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }catch (Exception e){
            logger.error("cannot create table Accoutn");
            throw e;
        }
        sessionManager.commitSession();
        logger.info("created table User");
    }

    private void createTableAccount(SessionManager sessionManager) throws SQLException {
        sessionManager.beginSession();
        Connection connection = sessionManager.getCurrentSession().getConnection();
        try (PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }catch (Exception e){
            logger.error("cannot create table Account");
            throw e;
        }
        sessionManager.commitSession();
        logger.info("created table Account");
    }

    private void createDBSchema(SessionManager sessionManager) throws SQLException {
        createTableUser(sessionManager);
        createTableAccount(sessionManager);
    }

    public static void main(String[] args) throws SQLException {

        DataSource dataSource = new DataSourceH2();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        Main myORM = new Main();
        myORM.createDBSchema(sessionManager);

        User user = new User(0, "ИвановИИ", 25);
        DbExecutor<User> dbExecutorUser = new DbExecutor<>(sessionManager);
        dbExecutorUser.create(user);

        user = dbExecutorUser.load(1, User.class);
        logger.info(user.toString());
        user.addAge(3);
        dbExecutorUser.update(user);
        user = dbExecutorUser.load(1, User.class);
        logger.info(user.toString());
        User user1 = new User(2, "СидоровВП", 34);
        dbExecutorUser.createOrUpdate(user1);
        user1 = dbExecutorUser.load(2, User.class);
        logger.info(user1.toString());
        user1.addAge(-2);
        dbExecutorUser.createOrUpdate(user1);
        user1 = dbExecutorUser.load(2, User.class);
        logger.info(user1.toString());

        User user2 = dbExecutorUser.load(3, User.class);

        Account account = new Account(1, "normal", 5000);
        DbExecutor<Account> dbExecutorAccount = new DbExecutor<>(sessionManager);
        dbExecutorAccount.create(account);

        account = dbExecutorAccount.load(1,Account.class);
        logger.info(account.toString());
        account.changeRest(-20);
        dbExecutorAccount.update(account);
        account = dbExecutorAccount.load(1, Account.class);
        logger.info(account.toString());
        Account account1 = new Account(2, "new", 2000);
        dbExecutorAccount.createOrUpdate(account1);
        account1 = dbExecutorAccount.load(2, Account.class);
        logger.info(account1.toString());
        account1.changeRest(-500);
        dbExecutorAccount.createOrUpdate(account1);
        account1 = dbExecutorAccount.load(2, Account.class);
        logger.info(account1.toString());

    }


}
