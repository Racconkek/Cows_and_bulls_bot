package exceptions;

public class SessionServerException extends Exception {

  public SessionServerException() { super("Session server error"); }

  public SessionServerException(String message) { super(message); }

}
