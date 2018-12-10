package core.session;

import core.player.IPlayer;
import core.player.User;

public class Session {
  private IPlayer first;
  private IPlayer second;
  private String id;

  public Session(IPlayer first, IPlayer second, String id){
    this.first = first;
    this.second = second;
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public IPlayer getFirst() {
    return first;
  }

  public IPlayer getSecond() {
    return second;
  }

  public IPlayer getOther(IPlayer user){
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
