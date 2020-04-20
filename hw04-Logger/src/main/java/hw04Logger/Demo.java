package hw04Logger;

public class Demo {
  public static void main(String[] args) {
    TestLogging testLogging = IoC.createProxyLogging();
    Demo demo = new Demo();
    demo.action(testLogging);
  }
  public void action(TestLogging testLogging) {
    testLogging.calculation(6);
  }
}



