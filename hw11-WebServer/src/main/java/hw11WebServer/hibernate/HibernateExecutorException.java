package hw11WebServer.hibernate;

public class HibernateExecutorException extends  RuntimeException{
    public HibernateExecutorException(Exception e) {
        super(e);
    }
}