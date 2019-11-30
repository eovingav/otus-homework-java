package ru.otus.hw08JSONserializer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SimpleObject {
    Date birthday;
    int age;
    String name;
    List<SimpleObject> children;
    String[] fio;
    int[] birthdayParts;


    public SimpleObject(Date birthday, int age, String name) {
        this.birthday = birthday;
        this.age = age;
        this.name = name;
        fio = new String[3];
        fio[0] = name;
        fio[1] = name;
        fio[2] = name;
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        birthdayParts = new int[3];
        birthdayParts[0] = cal.get(Calendar.DAY_OF_MONTH);
        birthdayParts[1] = cal.get(Calendar.MONTH);
        birthdayParts[2] = cal.get(Calendar.YEAR);
        children = new ArrayList<>();
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void addChild(SimpleObject object){

        children.add(object);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleObject{" +
                "birthday=" + birthday +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", children=");
        for (SimpleObject child: children){
            sb.append(child.getName());
        }
        sb.append('}');
        return sb.toString();
    }
}
