package ru.otus.hw02DIYArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DIYArrayListTest {

    private Integer[] initData = {5, 10, 15, 20, 25, 30, 35, 40, 50, 60, 70, 80, 90, 100, 33, 44, 28, 72, 97, 45};

    @Test
    @DisplayName("Collections add all")
    void collectionsAddAllInteger() throws Exception {
        boolean resultAdd;
        List<Integer> diyArrayList= new DIYArrayList<>();
        resultAdd = Collections.addAll(diyArrayList, initData);
        assertTrue(resultAdd);
        assertEquals(diyArrayList.size(), initData.length);
    }

    @Test
    @DisplayName("Collections copy")
    void collectionsCopyInteger() throws Exception {
        boolean resultAdd;
        List<Integer> diyArrayList= new DIYArrayList<>();
        resultAdd = Collections.addAll(diyArrayList, initData);
        assertTrue(resultAdd);
        int srcSize = diyArrayList.size();
        List<Integer> newDIYArrayList = new DIYArrayList<>(srcSize);
        Collections.copy(newDIYArrayList, diyArrayList);
        ListIterator<Integer> srcIterator = diyArrayList.listIterator();
        for (Integer newItem : newDIYArrayList) {
            assertEquals(newItem, srcIterator.next());
        }
    }

    @Test
    @DisplayName("Collections sort")
    void collectionsSortInteger() throws Exception {
        boolean resultAdd;
        List<Integer> diyArrayList = new DIYArrayList<>();
        resultAdd = Collections.addAll(diyArrayList, initData);
        assertTrue(resultAdd);
        int size = diyArrayList.size();
        Collections.sort(diyArrayList);
        for (int i=0; i < size - 1; i++){
            assertTrue(diyArrayList.get(i) <= diyArrayList.get(i+1));
        }
    }

}
