package hw04Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class IoC {

  static TestLogging createProxyLogging() {
    Set<Method> annotatedMethods = new HashSet<>();
    Method[] methods = TestLogging.class.getDeclaredMethods();
    for(Method method: methods){
      Annotation logAnnotation = method.getAnnotation(Log.class);
      if (Objects.nonNull(logAnnotation)){
        annotatedMethods.add(method);
      }
    }
    InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl(), annotatedMethods);
    return (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{TestLogging.class}, handler);
  }

  static class DemoInvocationHandler implements InvocationHandler {
    private final TestLogging myClass;
    private final Set<Method> annotatedMethods;

    DemoInvocationHandler(TestLogging myClass, Set<Method> annotatedMethods) {
      this.myClass = myClass;
      this.annotatedMethods = annotatedMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (annotatedMethods.contains(method)){
        StringBuilder sb = new StringBuilder();
        sb.append("executed method: ");
        sb.append(method.getName());
        for (Object param: args){
          sb.append(", param:");
          sb.append(param);
        }
        System.out.println(sb.toString());
      }
      return method.invoke(myClass, args);
    }

    @Override
    public String toString() {
      return "DemoInvocationHandler{" +
                 "myClass=" + myClass +
                 '}';
    }
  }

}
