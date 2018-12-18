package tests.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.primitives.HandlerAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandlerAnswerUnitTests{
  private HandlerAnswer handlerAnswer;

  @BeforeEach
  void setUp() {
    handlerAnswer = new HandlerAnswer("firstMsg", "secondMsg", false);
  }

  @Test
  void getFirstAnswer_shouldReturnRightValue(){
    var expected = "firstMsg";

    var actual = handlerAnswer.getFirstAnswer();

    assertEquals(expected, actual);
  }

  @Test
  void getFirstAnswer_gettingAfterReCreation_shouldReturnRightValue(){
    handlerAnswer = new HandlerAnswer("changeFirstMsg", "secondMsg", false);
    var expected = "changeFirstMsg";

    var actual = handlerAnswer.getFirstAnswer();

    assertEquals(expected, actual);
  }


  @Test
  void getSecondAnswer_shouldRturnRightValue(){
    var expected = "secondMsg";

    var actual = handlerAnswer.getSecondAnswer();

    assertEquals(expected, actual);
  }

  @Test
  void getSecondAnswer_gettingAfterReCreation_shouldReturnRightValue(){
    handlerAnswer = new HandlerAnswer("firstMsg", "changeSecondMsg", false);
    var expected = "changeSecondMsg";

    var actual = handlerAnswer.getSecondAnswer();

    assertEquals(expected, actual);
  }

  @Test
  void isEndSession_shouldReturnFalse(){
    assertFalse(handlerAnswer.isEndSession());
  }

  @Test
  void isEndSession_shouldReturnTrue(){
    var handlerAnswer = new HandlerAnswer("firstMsg", "second", true);
    assertTrue(handlerAnswer.isEndSession());
  }

}
