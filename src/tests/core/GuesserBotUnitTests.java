package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.player.GuesserBot;
import core.primitives.CowsAndBulls;
import core.primitives.GameState;
import core.primitives.GameStatus;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.handler.GuesserBotAnswerHandler;

public class GuesserBotUnitTests {
  private GuesserBot bot;
  private GameState state;

  @BeforeEach
  void setUp() {
    state = new GameState(new CowsAndBulls(), 1234, GameStatus.IN_PROCESS);
    bot = new GuesserBot("iChatId", new GuesserBotAnswerHandler(state));
  }

  @Test
  void getName_shouldReturnRightValue(){
    var expected = "GuesserBot";

    var actual = bot.getName();

    assertEquals(expected,actual);
  }

  @Test
  void getChatID_shouldReturnRightValue(){
    var expected = "iChatId";

    var actual = bot.getChatID();

    assertEquals(expected,actual);
  }

  @Test
  void getTries_shouldReturnRightValue(){
    var expected = 0;

    var actual = (int)bot.getTries();

    assertEquals(expected,actual);
  }

  @Test
  void getTries_gettingAfterIncrease_shouldReturnRightValue(){
    var expected = 2;
    bot.increaseTries();
    bot.increaseTries();

    var actual = (int)bot.getTries();

    assertEquals(expected,actual);
  }

  @Test
  void getRole_shouldReturnRightValue(){
    var expected = UserGameRole.GUESSER;

    var actual = bot.getRole();

    assertEquals(expected,actual);
  }

  @Test
  void increaseTries_shouldReturnRightValue(){
    var expected = 3;
    bot.increaseTries();
    bot.increaseTries();
    bot.increaseTries();

    var actual = (int)bot.getTries();

    assertEquals(expected, actual);
  }
}
