package core.session;

import com.google.inject.Guice;
import com.google.inject.Inject;
import core.BasicModule;
import core.player.GuesserBot;
import core.player.IPlayer;
import core.player.RiddlerBot;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;
import tools.handler.GuesserBotAnswerHandler;
import tools.handler.RiddleBotAnswerHandler;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionServer implements ISessionServer {

  private final ConcurrentMap<String, Session> idToSession;
  private final ConcurrentMap<IPlayer, String> userToCurrentSessionId;

  @Inject
  public SessionServer() {
    idToSession = new ConcurrentHashMap<>();
    userToCurrentSessionId = new ConcurrentHashMap<>();
  }

  @Override
  public Session createSession(IPlayer firstUser, IPlayer secondUser) throws SessionServerException {
    if (hasSessionWithPlayer(firstUser)) {
      throw new SessionServerException(String.format("User %s already have session", firstUser.getName()));
    }
    if (hasSessionWithPlayer(secondUser)) {
      throw new SessionServerException(String.format("User %s already have session", secondUser.getName()));
    }
    var id = createIdForSession();
    var session = new Session(firstUser, secondUser, id);
    idToSession.putIfAbsent(id, session);
    userToCurrentSessionId.putIfAbsent(firstUser, session.getId());
    userToCurrentSessionId.putIfAbsent(secondUser, session.getId());

    return session;
  }

  private String createIdForSession() {
    return UUID.randomUUID().toString();
  }


  @Override
  public Session createSessionWithGuesserBot(IPlayer user) throws SessionServerException {
    if (hasSessionWithPlayer(user)) {
      throw new SessionServerException(String.format("User %s already have session", user.getName()));
    }
    user.setRole(UserGameRole.RIDDLER);
    var id = createIdForSession();
    var injector = Guice.createInjector(new BasicModule());
    var bot = new GuesserBot(user.getChatID(), injector.getInstance(GuesserBotAnswerHandler.class));
    var session = new Session(user, bot, id);

    idToSession.putIfAbsent(id, session);
    userToCurrentSessionId.putIfAbsent(user, session.getId());
    return session;
  }

  @Override
  public Session createSessionWithRiddlerBot(IPlayer user) throws SessionServerException{
    if (hasSessionWithPlayer(user)) {
      throw new SessionServerException(String.format("User %s already have session", user.getName()));
    }

    user.setRole(UserGameRole.GUESSER);
    var id = createIdForSession();
    var injector = Guice.createInjector(new BasicModule());
    var bot = new RiddlerBot(user.getChatID(), injector.getInstance(RiddleBotAnswerHandler.class));
    var session = new Session(user, bot, id);

    idToSession.putIfAbsent(id, session);
    userToCurrentSessionId.putIfAbsent(user, session.getId());
    return session;
  }

  @Override
  public HashSet<Session> getSessions() {
    return new HashSet<>(idToSession.values());
  }

  @Override
  public Session getSession(String sessionId) throws SessionServerException {
    var session = getSessionElseNull(sessionId);

    if (session == null) {
      throwNotThatSessionException(sessionId);
    }

    return session;
  }

  @Override
  public Session getSessionElseNull(String sessionId) {
    return idToSession.get(sessionId);
  }

  @Override
  public Session getSessionWithPlayer(IPlayer user) throws SessionServerException {
    var session = getSessionWithPlayerElseNull(user);

    if (session == null) {
      throw new SessionServerException(String.format("No session with user: %s", user.getName()));
    }

    return session;
  }

  @Override
  public Session getSessionWithPlayerElseNull(IPlayer user) {
    var id = userToCurrentSessionId.get(user);

    if (id == null) {
      return null;
    }

    return idToSession.get(id);
  }

  @Override
  public boolean hasSessionWithPlayer(IPlayer user) {
    return userToCurrentSessionId.containsKey(user);
  }

  @Override
  public void endSession(Session session) throws SessionServerException {
    endSession(session.getId());
  }

  @Override
  public void endSession(String sessionId) throws SessionServerException {
    var session = idToSession.get(sessionId);

    if (session == null) {
      throwNotThatSessionException(sessionId);
    }

    var first = session.getFirst();
    var second = session.getSecond();

    userToCurrentSessionId.remove(first);
    userToCurrentSessionId.remove(second);

    idToSession.remove(sessionId);
  }

  @Override
  public boolean hasSession(String sessionId) {
    return userToCurrentSessionId.containsValue(sessionId);
  }

  private void throwNotThatSessionException(String id) throws SessionServerException {
    throw new SessionServerException(String.format("No that session with id: %s", id));
  }
}
