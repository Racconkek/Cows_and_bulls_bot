package core.session;

import core.primitives.User;

public class Session {
  private User first;
  private User second;
  private String id;

  Session(User first, User second, String id){
    this.first = first;
    this.second = second;
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public User getFirst() {
    return first;
  }

  public User getSecond() {
    return second;
  }

  public User getOther(User user){
    if (user.equals(first))
      return second;
    return first;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Session){
      Session other = (Session)obj;
      return this.id.equals(other.id) && this.first.equals(other.first) && this.second.equals(other.second);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString(){
    return String.format("First: %s, Second: %s, ID: %S", first.toString(), second.toString(), id);
  }
}
