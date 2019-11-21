package ru.otus.hw08JSONserializer;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        JSONserializer serialiser = new JSONserializer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1986, 6, 10);
        SimpleObject object = new SimpleObject(calendar.getTime(), 30, "Peter");
        calendar.set(1999, 6, 10);
        SimpleObject child1 = new SimpleObject(calendar.getTime(), 18, "Ann");
        calendar.set(2005, 6, 10);
        SimpleObject child2 = new SimpleObject(calendar.getTime(), 25, "Matew");
        object.addChild(child1);
        object.addChild(child2);
        String jsonString = serialiser.toJSON(object);
        System.out.println(jsonString);
    }
}
