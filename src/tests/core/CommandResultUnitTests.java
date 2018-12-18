package core;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.primitives.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandResultUnitTests {
  private CommandResult commandResult;

  @BeforeEach
  void setUp() {
    commandResult = new CommandResult("firstMsg", "secondMsg");
  }


  @Test
  void getFirstMassage_shouldReturnRightValue(){
    var expected = "firstMsg";

    var actual = commandResult.getFirstMessage();

    assertEquals(expected, actual);
  }

  @Test
  void getFirstMassage_gettingAfterReCreation_shouldReturnRightValue(){
    commandResult = new CommandResult("changeFirstMsg", "secondMsg");
    var expected = "changeFirstMsg";

    var actual = commandResult.getFirstMessage();

    assertEquals(expected, actual);
  }


  @Test
  void getSecondMassage_shouldReturnRightValue(){
    var expected = "secondMsg";

    var actual = commandResult.getSecondMessage();

    assertEquals(expected, actual);
  }

  @Test
  void getSecondMassage_gettingAfterReCreation_shouldReturnRightValue(){
    commandResult = new CommandResult("firstMsg", "changeSecondMsg");
    var expected = "changeSecondMsg";

    var actual = commandResult.getSecondMessage();

    assertEquals(expected, actual);
  }

  @Test
  void equals_shouldReturnFalse(){
    assertFalse(commandResult.equals(10));
    assertFalse(commandResult.equals("firstMsg"));
    assertFalse(commandResult.equals(new CommandResult("firstMsg", null)));
    assertFalse((new CommandResult("firstMsg", null).equals(commandResult)));
    assertFalse(commandResult.equals(new CommandResult(null, "secondMsg")));
    assertFalse(commandResult.equals(null));
  }

  @Test
  void equals_shouldReturnTrue(){
    assertTrue(commandResult.equals(new CommandResult("firstMsg", "secondMsg")));
  }

}
