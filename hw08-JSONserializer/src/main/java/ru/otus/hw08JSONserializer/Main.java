package ru.otus.hw08JSONserializer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        int[] arrayInt = new int[3];
        for (int i = 0; i < 3; i++){
            arrayInt[i] = i + 1;
        }
        jsonString = serialiser.toJSON(arrayInt);
        System.out.println(jsonString);

        String[] arrayString = new String[3];
        for (int i = 0; i < 3; i++){
            arrayString[i] = "поле" + i;
        }
        jsonString = serialiser.toJSON(arrayString);
        System.out.println(jsonString);

        SimpleObject[] objArray = new SimpleObject[3];
        objArray[0] = child1;
        objArray[1] = child2;
        objArray[2] = null;

        jsonString = serialiser.toJSON(objArray);
        System.out.println(jsonString);

        List<String> simpleList = new ArrayList<>();
        simpleList.add("В лесу");
        simpleList.add("родилась");
        simpleList.add("ёлочка");

        jsonString = serialiser.toJSON(simpleList);
        System.out.println(jsonString);

        String str = "В лесу родилась ёлочка, в лесу она росла";
        jsonString = serialiser.toJSON(str);
        System.out.println(jsonString);

        Object obj = null;
        jsonString = serialiser.toJSON(obj);
        System.out.println(jsonString);

        boolean checker = true;
        jsonString = serialiser.toJSON(checker);
        System.out.println(jsonString);

        int counter = 20;
        jsonString = serialiser.toJSON(counter);
        System.out.println(jsonString);

    }
}
