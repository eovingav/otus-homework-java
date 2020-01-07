package hw15MessageSystem.messagesystem;

public enum MessageType {
  USER_AUTH("UserAuth"),
  USER_ADD("UserAdd"),
  USER_LIST("UserList");

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
