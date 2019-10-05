package ru.otus.hw03TestFramework;

import ru.otus.hw03TestFramework.DIYTestFramework.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException{

        if (args.length == 0){
            throw new RuntimeException("Test class name argument is not specified");
        }

        String className = args[0];
        TestRunner.run(className);
    }
}
