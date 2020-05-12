package ru.otus.hw12CacheEngine.hibernate;

public class HibernateExecutorException extends  RuntimeException{
    public HibernateExecutorException(Exception e) {
        super(e);
    }
}