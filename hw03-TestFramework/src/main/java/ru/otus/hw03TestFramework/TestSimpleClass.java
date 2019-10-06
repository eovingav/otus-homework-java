package ru.otus.hw03TestFramework;

import ru.otus.hw03TestFramework.DIYTestFramework.After;
import ru.otus.hw03TestFramework.DIYTestFramework.Before;
import ru.otus.hw03TestFramework.DIYTestFramework.Test;

public class TestSimpleClass {
    private int a;
    private int b;
    private SimpleClass testClassObj;

    @Before
    public void prepareTest(){
        a = 10;
        b = 20;
        testClassObj = new SimpleClass();
    }

    @Test
    public void getSumTest(){
        int sum = testClassObj.getSum(a, b);
        if (sum!= 30)
            throw new RuntimeException("sum error");
    }

    @Test
    public void getSub(){
        int sub = testClassObj.getSub(a, b);
        if (sub != -20)
            throw new RuntimeException("sub error");

    }

    @Test
    public void getDivTest(){
        double div = testClassObj.getDiv(a, b);
        if (div != (double)0.5)
            throw new RuntimeException("div error");
    }

    @Test
    public void getMultTest(){
        long mult = testClassObj.getMult(a, b);
        if (mult != (long)200)
            throw new RuntimeException("mult error");
    }

    @After
    public void finishTest(){
        a = 0;
        b = 0;
    }
}
