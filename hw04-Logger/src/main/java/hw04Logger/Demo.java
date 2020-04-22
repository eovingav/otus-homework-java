package hw04Logger;

import java.lang.reflect.InvocationTargetException;

public class Demo {
  public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    IoC<TestLogging> ioC = new IoC<>();
    TestLogging testLogging = ioC.createProxyLogging(new TestLoggingImpl(), TestLogging.class);
    Demo demo = new Demo();
    demo.action(testLogging);
  }

  public void action(TestLogging testLogging) {
    testLogging.calculation(6);
    testLogging.preparation("hello");
  }

}



