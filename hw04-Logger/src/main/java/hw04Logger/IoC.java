package hw04Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class IoC<T> {

  T createProxyLogging(T obj, Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Set<Method> annotatedMethods = getAnnotatedMethods(obj.getClass(), clazz);
    InvocationHandler handler = new DemoInvocationHandler(obj, annotatedMethods);
    return (T) Proxy.newProxyInstance(IoC.class.getClassLoader(),
            new Class<?>[]{clazz}, handler);
  }

  private Set<Method> getAnnotatedMethods(Class<?> clazzObject, Class<?> clazz) {

    Set<Method> annotatedMethods = new HashSet<>();
    Method[] methods = clazzObject.getDeclaredMethods();
    Method[] interfaceMethods = clazz.getMethods();
    for(Method method: methods){
      Annotation logAnnotation = method.getAnnotation(Log.class);
      if (Objects.nonNull(logAnnotation)){
        String methodSignature = getMethodSignature(method);
        for (Method methodInterface: interfaceMethods) {
          if (getMethodSignature(methodInterface).equals(methodSignature))
          {
            annotatedMethods.add(methodInterface);
            break;
          }
        }
      }
    }
    return annotatedMethods;
  }
  private String getMethodSignature(Method method){
    String fullMethodSiganature = method.toString();
    int beginMethodSignature = fullMethodSiganature.lastIndexOf(".");
    return fullMethodSiganature.substring(beginMethodSignature);
  }

  class DemoInvocationHandler implements InvocationHandler {
    private final T myClass;
    private final Set<Method> annotatedMethods;

    DemoInvocationHandler(T myClass, Set<Method> annotatedMethods) {
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