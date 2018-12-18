package core.queue;

import core.player.IPlayer;
import exceptions.UserQueueException;

public interface IUserQueue {
  boolean enqueue(IPlayer user);
  boolean isEmpty();
  boolean hasUser(IPlayer user);
  int size();
  IPlayer dequeue() throws UserQueueException;
}
