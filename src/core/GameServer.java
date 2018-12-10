package core;


import com.google.inject.Inject;
import core.queue.IUserQueue;
import core.session.ISessionServer;
import core.userdb.IUserDataBase;

public class GameServer implements IGameServer
{
  private final IUserQueue playerQueue;
  private final ISessionServer sessionServer;
  private final IUserDataBase userDataBase;

  @Inject
  public GameServer(IUserQueue playerQueue, ISessionServer sessionServer, IUserDataBase userDataBase) {
    this.playerQueue = playerQueue;
    this.sessionServer = sessionServer;
    this.userDataBase = userDataBase;
  }


  @Override
  public IUserQueue playerQueue() {
    return playerQueue;
  }

  @Override
  public ISessionServer sessionServer() {
    return sessionServer;
  }

  @Override
  public IUserDataBase userDataBase() { return userDataBase; }
}
