package core.queue;

import core.player.IPlayer;
import core.player.User;
import exceptions.UserQueueException;
import org.glassfish.grizzly.utils.Pair;

public interface IUserQueue {
  boolean enqueue(IPlayer user);
  boolean hasPair();
  boolean isEmpty();
  boolean hasUser(IPlayer user);
  int size();
  Pair<IPlayer, IPlayer> dequeuePair() throws UserQueueException;
  Pair<IPlayer, IPlayer> dequeuePairElseNull();
}
