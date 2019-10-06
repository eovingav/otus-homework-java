package ru.otus.hw09DIYORM.model;

import ru.otus.hw09DIYORM.ORM.ID;

public class User {

    @ID
    private long id;

    private String name;
    private int age;

    public User(long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void addAge(int age){
        this.age = this.age + age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
