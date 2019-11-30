import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw08JSONserializer.JSONserializer;
import ru.otus.hw08JSONserializer.SimpleObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONserializerTest {

    @Test
    @DisplayName("Test Object toJSON")
    void objectToJSONTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1986, 6, 10);
        SimpleObject object = new SimpleObject(calendar.getTime(), 30, "Peter");
        calendar.set(1999, 6, 10);
        SimpleObject child1 = new SimpleObject(calendar.getTime(), 18, "Ann");
        calendar.set(2005, 6, 10);
        SimpleObject child2 = new SimpleObject(calendar.getTime(), 25, "Matew");
        object.addChild(child1);
        object.addChild(child2);
        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(object);
        String jsonExample = "{\"birthday\":\"1986.07.10\",\"age\":\"30\",\"name\":\"Peter\",\"children\":[{\"birthday\":\"1999.07.10\",\"age\":\"18\",\"name\":\"Ann\",\"children\":[],\"fio\":[\"Ann\",\"Ann\",\"Ann\"],\"birthdayParts\":[\"10\",\"6\",\"1999\"]},{\"birthday\":\"2005.07.10\",\"age\":\"25\",\"name\":\"Matew\",\"children\":[],\"fio\":[\"Matew\",\"Matew\",\"Matew\"],\"birthdayParts\":[\"10\",\"6\",\"2005\"]}],\"fio\":[\"Peter\",\"Peter\",\"Peter\"],\"birthdayParts\":[\"10\",\"6\",\"1986\"]}";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test array primitive toJSON")
    void arrayPrimitiveToJSONTest() throws Exception {

        int[] arrayInt = new int[3];

        for (int i = 0; i < 3; i++){
            arrayInt[i] = i + 1;
        }

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(arrayInt);
        String jsonExample = "[\"1\",\"2\",\"3\"]";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test array Object toJSON 1")
    void arrayObject1ToJSONTest() throws Exception {

        String[] arrayString = new String[3];
        for (int i = 0; i < 3; i++){
            arrayString[i] = "поле" + i;
        }

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(arrayString);
        String jsonExample = "[\"поле0\",\"поле1\",\"поле2\"]";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test array Object toJSON 2")
    void arrayObject2ToJSONTest() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, 6, 10);
        SimpleObject child1 = new SimpleObject(calendar.getTime(), 18, "Ann");
        calendar.set(2005, 6, 10);
        SimpleObject child2 = new SimpleObject(calendar.getTime(), 25, "Matew");

        SimpleObject[] objArray = new SimpleObject[3];
        objArray[0] = child1;
        objArray[1] = child2;
        objArray[2] = null;

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(objArray);
        String jsonExample = "[{\"birthday\":\"1999.07.10\",\"age\":\"18\",\"name\":\"Ann\",\"children\":[],\"fio\":[\"Ann\",\"Ann\",\"Ann\"],\"birthdayParts\":[\"10\",\"6\",\"1999\"]},{\"birthday\":\"2005.07.10\",\"age\":\"25\",\"name\":\"Matew\",\"children\":[],\"fio\":[\"Matew\",\"Matew\",\"Matew\"],\"birthdayParts\":[\"10\",\"6\",\"2005\"]},\"null\"]";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test collection toJSON")
    void collectionToJSONTest() throws Exception {

        List<String> simpleList = new ArrayList<>();
        simpleList.add("В лесу");
        simpleList.add("родилась");
        simpleList.add("ёлочка");

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(simpleList);
        String jsonExample = "[\"В лесу\",\"родилась\",\"ёлочка\"]";
        assertEquals(jsonString, jsonExample);
    }

    @Test
    @DisplayName("Test String toJSON")
    void stringToJSONTest() throws Exception {

        String str = "В лесу родилась ёлочка, в лесу она росла";

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(str);
        String jsonExample = "\"В лесу родилась ёлочка, в лесу она росла\"";
        assertEquals(jsonString, jsonExample);
    }

    @Test
    @DisplayName("Test Null toJSON")
    void nullToJSONTest() throws Exception {

        Object obj = null;

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(obj);
        String jsonExample = "\"null\"";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test boolean toJSON")
    void booleanToJSONTest() throws Exception {

        boolean checker = true;

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(checker);
        String jsonExample = "\"true\"";
        assertEquals(jsonString, jsonExample);

    }

    @Test
    @DisplayName("Test number toJSON")
    void numberToJSONTest() throws Exception {

        int counter = 20;

        JSONserializer serializer = new JSONserializer();
        String jsonString = serializer.toJSON(counter);
        String jsonExample = "\"20\"";
        assertEquals(jsonString, jsonExample);
    }
}

