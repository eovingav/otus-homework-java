package ru.otus.hw09DIYORM.api.sessionmanager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}
