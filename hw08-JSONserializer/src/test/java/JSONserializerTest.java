import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw08JSONserializer.JSONserializer;
import ru.otus.hw08JSONserializer.SimpleObject;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONserializerTest {

    @Test
    @DisplayName("Test toJSON")
    void toJSONTest() throws Exception {
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
}

