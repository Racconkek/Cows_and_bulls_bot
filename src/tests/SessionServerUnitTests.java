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
import static org.junit.jupiter.api.Assertions.assertThrows;

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


}
