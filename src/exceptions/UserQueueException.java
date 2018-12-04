package exceptions;

public class UserQueueException extends Exception {

  public UserQueueException() {
    super("Player queue error");
  }

  public UserQueueException(String message) {
    super(message);
  }
}
