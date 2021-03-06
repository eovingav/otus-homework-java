package hw11WebServer.api.sessionmanager;

public interface SessionManager extends AutoCloseable {
  void beginSession();
  void commitSession();
  void rollbackSession();
  void close();

  DatabaseSession getCurrentSession();
}
