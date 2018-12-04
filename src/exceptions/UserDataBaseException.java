package exceptions;

public class UserDataBaseException extends Exception {
  public UserDataBaseException() {
    super("User database exception");
  }

  public UserDataBaseException(String message) {
    super(message);
  }
}
