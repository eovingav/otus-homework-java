package hw15MessageSystem.db.hibernate;

public class HibernateExecutorException extends  RuntimeException{
    public HibernateExecutorException(Exception e) {
        super(e);
    }
}