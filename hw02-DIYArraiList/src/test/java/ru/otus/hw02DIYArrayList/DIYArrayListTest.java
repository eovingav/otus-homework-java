package ru.otus.hw02DIYArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DIYArrayListTest {

    @Test
    @DisplayName("Collections add all")
    void collectionAddAllInteger() throws Exception {
        boolean resultAdd = false;
        List<Integer> diyArrayList= new DIYArrayList<>();
        Integer[] arrayInt = {5, 10, 15, 20, 25, 30, 35, 40, 50, 60, 70, 80, 90, 100};
        resultAdd = Collections.addAll(diyArrayList, arrayInt);
        assertTrue(resultAdd);
    }

}
