package tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.player.IPlayer;
import core.player.User;
import core.primitives.CowsAndBulls;
import core.primitives.GameState;
import core.primitives.GameStatus;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.handler.GuesserBotAnswerHandler;

public class GuesserBotAnswerHandlerUnitTests {
  private GameState state;
  private GuesserBotAnswerHandler handler;
  private IPlayer user;

  @BeforeEach
  void setUp(){
    state = new GameState(new CowsAndBulls(), 1234, GameStatus.IN_PROCESS);
    handler = new GuesserBotAnswerHandler(state);
    user = new User("iUser", "iChatId", UserGameRole.RIDDLER);
  }

  @Test
  void handleInput_shouldReturnRightValue(){
    var input = "0 0";

    var actual = handler.handleInput(input, user);

    assertEquals(actual.getSecondAnswer(), null);
    assertFalse(actual.isEndSession());
  }

  @Test
  void handleInput_withOtherArgs_shouldReturnRightValue(){
    var input = "4 0";

    var actual = handler.handleInput(input, user);

    assertEquals(actual.getSecondAnswer(), null);
    assertFalse(actual.isEndSession());
  }

  @Test
  void handleInput_1and3cowsAndBulls_shouldReturnRightValue(){
    var input = "1 3";

    var actual = handler.handleInput(input, user);

    assertEquals(actual.getSecondAnswer(), null);
    assertFalse(actual.isEndSession());
  }

  @Test
  void handleInput_endSissionTrue_shouldReturnRightValue(){
    var input = "0 4";

    var actual = handler.handleInput(input, user);

    assertEquals(actual.getSecondAnswer(), null);
    assertTrue(actual.isEndSession());
  }
}
