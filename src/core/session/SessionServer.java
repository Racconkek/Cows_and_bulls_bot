package core.session;

import com.google.inject.Inject;
import core.primitives.User;
import exceptions.SessionServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SessionServer implements ISessionServer {

  private final Map<String, Session> idToSession;
  private final Map<User, String> userToCurrentSessionId;

  @Inject
  public SessionServer() {
    idToSession = new HashMap<>();
    userToCurrentSessionId = new HashMap<>();
  }

  @Override
  public Session createSession(User firstUser, User secondUser) throws SessionServerException {
    if (hasSessionWithUser(firstUser)) {
      throwAlreadyHaveSession(firstUser);
    }

    if (hasSessionWithUser(secondUser)) {
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
  public Session createAISessionForPlayer(User user) throws SessionServerException {
    return null;
  }

  @Override
  public List<Session> getSessions() {
    return new ArrayList<Session>(idToSession.values());
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
  public Session getSessionWithUser(User user) throws SessionServerException {
    var session = getSessionWithUserElseNull(user);

    if (session == null) {
      throw new SessionServerException(String.format("No session with user: %s", user.getName()));
    }

    return session;
  }

  @Override
  public Session getSessionWithUserElseNull(User user) {
    var id = userToCurrentSessionId.get(user);

    if (id == null) {
      return null;
    }

    return idToSession.get(id);
  }

  @Override
  public boolean hasSessionWithUser(User user) {
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

    if (first instanceof User)
      userToCurrentSessionId.remove(first);

    if (second instanceof User)
      userToCurrentSessionId.remove(second);

    idToSession.remove(sessionId);
  }

  @Override
  public boolean hasSession(String sessionId) {
    return userToCurrentSessionId.containsValue(sessionId);
  }

  private void throwAlreadyHaveSession(User user) throws SessionServerException {
    throw new SessionServerException(String.format("User %s already have session", user.getName()));
  }

  private void throwNotThatSessionException(String id) throws SessionServerException {
    throw new SessionServerException(String.format("No that session with id: %s", id));
  }
}
