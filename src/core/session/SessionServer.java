package core.session;

import com.google.inject.Guice;
import com.google.inject.Inject;
import core.BasicModule;
import core.handler.BotAnswerHandler;
import core.player.IPlayer;
import core.player.RiddlerBot;
import core.primitives.UserGameRole;
import exceptions.SessionServerException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class SessionServer implements ISessionServer {

  private final Map<String, Session> idToSession;
  private final Map<IPlayer, String> userToCurrentSessionId;

  @Inject
  public SessionServer() {
    idToSession = new HashMap<>();
    userToCurrentSessionId = new HashMap<>();
  }

  @Override
  public Session createSession(IPlayer firstUser, IPlayer secondUser) throws SessionServerException {
    if (hasSessionWithPlayer(firstUser)) {
      throwAlreadyHaveSession(firstUser);
    }

    if (hasSessionWithPlayer(secondUser)) {
      throwAlreadyHaveSession(secondUser);
    }

    var id = createIdForSession();
    var session = new Session(firstUser, secondUser, id);

    idToSession.put(id, session);
    userToCurrentSessionId.put(firstUser, session.getId());
    userToCurrentSessionId.put(secondUser, session.getId());

    return session;
  }

  private String createIdForSession() {
    return UUID.randomUUID().toString();
  }


  @Override
  public Session createAISessionForPlayer(IPlayer user) throws SessionServerException {
    if (hasSessionWithPlayer(user)) {
      throwAlreadyHaveSession(user);
    }

    user.setRole(UserGameRole.GUESSER);
    var id = createIdForSession();
    var injector = Guice.createInjector(new BasicModule());
    var bot = new RiddlerBot(user.getChatID(), injector.getInstance(BotAnswerHandler.class));
    var session = new Session(user, bot, id);

    idToSession.put(id, session);
    userToCurrentSessionId.put(user, session.getId());
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

  private void throwAlreadyHaveSession(IPlayer user) throws SessionServerException {
    throw new SessionServerException(String.format("User %s already have session", user.getName()));
  }

  private void throwNotThatSessionException(String id) throws SessionServerException {
    throw new SessionServerException(String.format("No that session with id: %s", id));
  }
}
