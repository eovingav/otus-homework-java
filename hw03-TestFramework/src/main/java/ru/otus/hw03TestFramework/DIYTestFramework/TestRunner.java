package ru.otus.hw03TestFramework.DIYTestFramework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestRunner {

    public static void run(String ClassName) throws ClassNotFoundException, NoSuchMethodException {

        ArrayList<Method> testMethods = new ArrayList<Method>();
        ArrayList<Method> beforeMethods = new ArrayList<Method>();
        ArrayList<Method> afterMethods = new ArrayList<Method>();

        Class<?> testClass = Class.forName(ClassName);
        Method[] methods = testClass.getDeclaredMethods();
        for(Method method:methods){

            Annotation beforeAnnotation = method.getAnnotation(Before.class);
            if (Objects.nonNull(beforeAnnotation)) {
                beforeMethods.add(method);
            }

            Annotation testAnnotation = method.getAnnotation(Test.class);
            if (Objects.nonNull(testAnnotation)) {
                testMethods.add(method);
            }

            Annotation afterAnnotation = method.getAnnotation(After.class);
            if (Objects.nonNull(afterAnnotation)) {
                afterMethods.add(method);
            }
        }

        int total = testMethods.size();
        int succeed = 0;
        int failed = 0;

        Map<Method,String> testStatus = new HashMap<>();
        Constructor<?> constructor = testClass.getConstructor();
        Object testObject = null;
        for (Method testMethod: testMethods){
            try{
                testObject = constructor.newInstance();

                for (Method beforeMethod: beforeMethods){
                    beforeMethod.invoke(testObject);
                }

                testMethod.invoke(testObject);

                succeed += 1;
                testStatus.put(testMethod, ": succeed");

            }catch (Exception e){
                System.out.println(testMethod.getName() + ":");
                e.getCause().printStackTrace();
                failed += 1;
                testStatus.put(testMethod, ": failed");
            }

            for (Method afterMethod: afterMethods){
                try {
                   afterMethod.invoke(testObject);
                }catch (Exception e){
                      System.out.println(afterMethod.getName() + ":");
                      e.getCause().printStackTrace();
                }
            }
        }

        for (Method testMethod: testMethods){
            System.out.println(testMethod.getName() + testStatus.get(testMethod));
        }

        System.out.println("total tests count:" + total + ", succeed:" + succeed + ", failed:" + failed);
    }

}
