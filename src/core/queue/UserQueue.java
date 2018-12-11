package core.queue;

import com.google.inject.Inject;
import core.player.IPlayer;
import core.player.User;
import exceptions.UserQueueException;

import java.util.LinkedList;
import java.util.Queue;

import org.glassfish.grizzly.utils.Pair;

public class UserQueue implements IUserQueue {

  private final Queue<IPlayer> queue;


  @Inject
  public UserQueue() {
    queue = new LinkedList<>();
  }


  @Override
  public boolean enqueue(IPlayer user) {
    if (user == null) {
      throw new IllegalArgumentException("attempt to enqueue null user");
    }

    if (!queue.contains(user))
    {
      queue.add(user);
      return true;
    }

    return false;
  }

  @Override
  public boolean hasPair() {
    return queue.size() >= 2;
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public boolean hasUser(IPlayer user) {
    return queue.contains(user);
  }

  @Override
  public int size() {
    return queue.size();
  }

  @Override
  public Pair<IPlayer, IPlayer> dequeuePair() throws UserQueueException {
    var pair = dequeuePairElseNull();

    if (pair == null) {
      throw new UserQueueException("There is no user to play with. Please wait.");
    }

    return pair;
  }

  @Override
  public Pair<IPlayer, IPlayer> dequeuePairElseNull() {
    if (!hasPair()) {
      return null;
    }

    var first = queue.remove();
    var second = queue.remove();

    return new Pair<>(first, second);
  }
}
