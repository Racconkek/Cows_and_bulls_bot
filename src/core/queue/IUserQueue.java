package core.queue;

import core.primitives.User;
import exceptions.UserQueueException;
import org.glassfish.grizzly.utils.Pair;

public interface IUserQueue {
  boolean enqueue(User user);
  boolean hasPair();
  boolean isEmpty();
  int size();
  Pair<User, User> dequeuePair() throws UserQueueException;
  Pair<User, User> dequeuePairElseNull();
}
