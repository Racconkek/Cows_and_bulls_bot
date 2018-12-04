package core;

import core.queue.IUserQueue;
import core.session.ISessionServer;
import core.userdb.IUserDataBase;

public interface IGameServer {
  IUserQueue playerQueue();
  ISessionServer sessionServer();
  IUserDataBase userDataBase();
}
