package ru.otus.hw09DIYORM.ORM;

public class DbExecutorException extends  RuntimeException{
        public DbExecutorException(Exception e) {
            super(e);
        }
}
