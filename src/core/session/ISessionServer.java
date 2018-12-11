package core.session;

import core.player.IPlayer;
import core.player.User;
import exceptions.SessionServerException;

import java.util.Set;

public interface ISessionServer {

  Session createSession(IPlayer firstUser, IPlayer secondUser) throws SessionServerException;

  Session createSessionWithRiddlerBot(IPlayer user) throws SessionServerException;

  Session createSessionWithGuesserBot(IPlayer user) throws SessionServerException;

  Set<Session> getSessions();

  Session getSession(String sessionId) throws SessionServerException;

  Session getSessionElseNull(String sessionId);

  Session getSessionWithPlayer(IPlayer user) throws SessionServerException;

  Session getSessionWithPlayerElseNull(IPlayer user);

  boolean hasSessionWithPlayer(IPlayer user);

  void endSession(Session session) throws SessionServerException;

  void endSession(String sessionId) throws SessionServerException;

  boolean hasSession(String sessionId);

}
