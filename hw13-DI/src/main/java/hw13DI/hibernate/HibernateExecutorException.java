package hw13DI.hibernate;

public class HibernateExecutorException extends  RuntimeException{
    public HibernateExecutorException(Exception e) {
        super(e);
    }
}