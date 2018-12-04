package core.session;

import core.primitives.User;
import exceptions.SessionServerException;
import java.util.List;

public interface ISessionServer {

  Session createSession(User firstUser, User secondUser) throws SessionServerException;

  Session createAISessionForPlayer(User user) throws SessionServerException;

  List<Session> getSessions();

  Session getSession(String sessionId) throws SessionServerException;

  Session getSessionElseNull(String sessionId);

  Session getSessionWithUser(User user) throws SessionServerException;

  Session getSessionWithUserElseNull(User user);

  boolean hasSessionWithUser(User user);

  void endSession(Session session) throws SessionServerException;

  void endSession(String sessionId) throws SessionServerException;

  boolean hasSession(String sessionId);

}
