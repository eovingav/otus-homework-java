package ru.otus.hw10Hibernate.hibernate.sessionmanager;

public class HibernateExecutorException extends  RuntimeException{
    public HibernateExecutorException(Exception e) {
        super(e);
    }
}