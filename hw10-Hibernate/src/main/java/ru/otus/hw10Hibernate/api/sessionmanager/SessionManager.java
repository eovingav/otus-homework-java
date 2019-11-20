package ru.otus.hw10Hibernate.api.sessionmanager;

import ru.otus.hw10Hibernate.api.sessionmanager.DatabaseSession;

public interface SessionManager extends AutoCloseable {
  void beginSession();
  void commitSession();
  void rollbackSession();
  void close();

  DatabaseSession getCurrentSession();
}
