package core.queue;

import com.google.inject.Inject;
import core.player.IPlayer;
import exceptions.UserQueueException;
import org.glassfish.grizzly.utils.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class UserQueue implements IUserQueue {

  private IPlayer firstUser;
  private IPlayer secondUser;
//  private final Queue<IPlayer> queue;


  @Inject
  public UserQueue() {
    firstUser = null;
    secondUser = null;
//    queue = new LinkedList<>();
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
//
//    if (!queue.contains(user)) {
//      queue.add(user);
//      return true;
//    }
//
//    return false;
  }

  @Override
  public synchronized boolean hasPair() {
    return firstUser != null && secondUser != null;
//    return queue.size() >= 2;
  }

  @Override
  public synchronized boolean isEmpty() {
    return firstUser == null && secondUser == null;
//    return queue.isEmpty();
  }

  @Override
  public synchronized boolean hasUser(IPlayer user) {
    return firstUser.equals(user) || secondUser.equals(user);
    //    return queue.contains(user);
  }

  @Override
  public synchronized int size() {
    var size = 0;
    if(firstUser != null)
      size++;
    if (secondUser != null)
      size++;
    return size;
//    return queue.size();
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

//    var first = queue.remove();
//    var second = queue.remove();
//
//    return new Pair<>(first, second);
  }
}
