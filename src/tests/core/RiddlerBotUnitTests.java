package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.player.RiddlerBot;
import core.primitives.UserGameRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.handler.RiddlerBotAnswerHandler;

public class RiddlerBotUnitTests {
  private RiddlerBot bot;

  @BeforeEach
  void setUp() {
    bot = new RiddlerBot("iChatId", new RiddlerBotAnswerHandler(new GameRules()));
  }

  @Test
  void getName_shouldReturnRightValue(){
    var expected = "RiddlerBot";

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
    var expected = UserGameRole.RIDDLER;

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
