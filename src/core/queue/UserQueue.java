package core.queue;

import com.google.inject.Inject;
import core.player.IPlayer;
import exceptions.UserQueueException;

public class UserQueue implements IUserQueue {

  private IPlayer waitingUser;

  @Inject
  public UserQueue() {
    waitingUser = null;
  }


  @Override
  public synchronized boolean enqueue(IPlayer user) {
    if (user == null) {
      throw new IllegalArgumentException("attempt to enqueue null user");
    }
    if (waitingUser == null) {
      waitingUser = user;
      return true;
    }
    return false;
  }

  @Override
  public synchronized boolean isEmpty() {
    return waitingUser == null;
  }

  @Override
  public synchronized boolean hasUser(IPlayer user) {
    if (waitingUser != null)
      return waitingUser.equals(user);
    return false;
  }

  @Override
  public synchronized int size() {
    if(waitingUser != null)
      return 1;
    return 0;
  }

  @Override
  public IPlayer dequeue() throws UserQueueException {
    if (waitingUser == null)
      throw new UserQueueException("No user to play with.");
    return waitingUser;
  }
}
