package tests;

import core.player.IPlayer;
import core.player.User;
import core.primitives.UserGameRole;
import core.session.Session;
import core.session.SessionServer;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionServerUnitTests {

  private SessionServer server = new SessionServer();
  private IPlayer first;
  private IPlayer second;

  @BeforeEach
  void SetUp() {
    first = new User("firstU", "firstID", UserGameRole.RIDDLER);
    second = new User("secondU", "secondID", UserGameRole.GUESSER);
  }

  @Test
  void createSession_shouldReturnRightValue() throws SessionServerException {
    var actual = server.createSession(first, second);
    var expected = new Session(first, second, server.getSessionWithPlayer(first).getId());

    assertEquals(expected, actual);
    assertEquals(1, server.getSessions().size());
  }

  @Test
  void createSession_creatingWithNotFreeUser_shouldThrowExeption()
      throws SessionServerException {
    server.createSession(first, second);
    var third = new User("thirdU", "thirdID", UserGameRole.RIDDLER);

    assertThrows(SessionServerException.class, () -> server.createSession(second, third));
  }


  @Test
  void getSession_shouldReturnRightValue() throws SessionServerException {
    server.createSession(first, second);
    var id = server.getSessionWithPlayer(first).getId();
    var expected = new Session(first, second, server.getSessionWithPlayer(first).getId());
    var actual = server.getSession(id);

    assertEquals(expected, actual);
  }

  @Test
  void getSession_gettingByUnexistingUser_shouldThrowExeption() throws SessionServerException {

    assertThrows(SessionServerException.class, () -> server.getSession(first.getChatID()));
  }

  @Test
  void getSessionWithPlayer_shouldReturnRightValue() throws SessionServerException {
    server.createSession(first, second);
    var expected = new Session(first, second, server.getSessionWithPlayer(first).getId());
    var actual = server.getSessionWithPlayer(first);
    var session_userS = server.getSessionWithPlayer(second);

    assertEquals(expected, actual);
    assertEquals(session_userS, actual);
  }

  @Test
  void hasSessionWithPlayer_shouldReturnRightValue() throws SessionServerException {
    server.createSession(first, second);
    var expected = true;
    var actual = server.hasSessionWithPlayer(first);

    assertEquals(expected, actual);
  }

  @Test
  void hasSessionWithPlayer_UnknownUser_shouldReturnRightValue() throws SessionServerException {
    server.createSession(first, second);
    var expected = false;
    var third = new User("thirdU", "thirdId", UserGameRole.RIDDLER);
    var actual = server.hasSessionWithPlayer(third);

    assertEquals(expected, actual);
  }

  @Test
  void endSessionById_shouldRightEndSession() throws SessionServerException {
    server.createSession(first, second);
    var idSession = server.getSessionWithPlayer(first).getId();
    server.endSession(idSession);

    assertFalse(server.hasSession(idSession));
  }

  @Test
  void endSessionById_endingUnexistingSession_shouldThrowException() throws SessionServerException {
    var idSession = "i unexisting session";

    assertThrows(SessionServerException.class, () -> server.endSession(idSession));
  }

  @Test
  void endSessionBySession_shouldRightEndSession() throws SessionServerException {
    server.createSession(first, second);
    var session = server.getSessionWithPlayer(first);
    server.endSession(session);

    assertFalse(server.hasSession(session.getId()));
  }

  @Test
  void endSessionBySession_endingUnexistingSession_shouldRightEndSession() throws SessionServerException {
    var session = new Session(first, second, "i unexisting session");

    assertThrows(SessionServerException.class, () -> server.endSession(session));
  }

  @Test
  void hasSession_shouldReturnTrue() throws SessionServerException {
    var session = server.createSession(first, second);


    assertTrue(server.hasSession(session.getId()));
  }


  @Test
  void hasSession_shouldReturnFalse() throws SessionServerException {
    server.createSession(first, second);
    var idSession = "i unexisting session";

    assertFalse(server.hasSession(idSession));
  }


}
