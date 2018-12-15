package core.queue;

import com.google.inject.Inject;
import core.player.IPlayer;
import exceptions.UserQueueException;
import org.glassfish.grizzly.utils.Pair;

public class UserQueue implements IUserQueue {

  private IPlayer firstUser;
  private IPlayer secondUser;

  @Inject
  public UserQueue() {
    firstUser = null;
    secondUser = null;
  }


  @Override
  public synchronized boolean enqueue(IPlayer user) {
    if (user == null) {
      throw new IllegalArgumentException("attempt to enqueue null user");
    }
    if (firstUser == null) {
      firstUser = user;
      return true;
    }
    if(secondUser == null) {
      secondUser = user;
      return true;
    }
    return false;
  }

  @Override
  public synchronized boolean hasPair() {
    return firstUser != null && secondUser != null;
  }

  @Override
  public synchronized boolean isEmpty() {
    return firstUser == null && secondUser == null;
  }

  @Override
  public synchronized boolean hasUser(IPlayer user) {
    var result = false;
    if (firstUser != null)
      result = firstUser.equals(user);
    if (secondUser != null)
      result = secondUser.equals(user);
    return result;
  }

  @Override
  public synchronized int size() {
    var size = 0;
    if(firstUser != null)
      size++;
    if (secondUser != null)
      size++;
    return size;
  }

  @Override
  public synchronized Pair<IPlayer, IPlayer> dequeuePair() throws UserQueueException {
    var pair = dequeuePairElseNull();

    if (pair == null) {
      throw new UserQueueException("There is no user to play with. Please wait.");
    }

    return pair;
  }

  @Override
  public synchronized Pair<IPlayer, IPlayer> dequeuePairElseNull() {
    if (!hasPair()) {
      return null;
    }
    var pair = new Pair<>(firstUser, secondUser);
    firstUser = null;
    secondUser = null;
    return pair;
  }
}
