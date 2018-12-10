package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.player.IPlayer;
import core.player.User;
import core.primitives.UserGameRole;
import core.session.Session;
import core.session.SessionServer;
import exceptions.SessionServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    assertEquals(actual, expected);
    assertEquals(1, server.getSessions().size());
  }

  @Test
  void createSession_creatingWithNotFreeUser_shouldReturnRightValue()
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

    assertEquals(actual, expected);
  }


}
