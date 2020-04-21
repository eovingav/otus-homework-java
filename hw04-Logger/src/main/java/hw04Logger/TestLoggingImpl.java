package hw04Logger;


public class TestLoggingImpl implements TestLogging {

  @Log
  @Override
  public void calculation(int param) {
    System.out.println("param:" + param);
  }

  public void preparation(String param){
    System.out.println("param:" + param);
  }
}
