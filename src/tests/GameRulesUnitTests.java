package tests;

import core.GameRules;
import core.player.IPlayer;
import core.player.User;
import core.primitives.CowsAndBulls;
import core.primitives.UserGameRole;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameRulesUnitTests {

  private IPlayer user;
  private List<Integer> hiddenNumber = List.of(1, 5, 4, 9);
  private GameRules rules = new GameRules();

  @BeforeEach
  void SetUp() {
    user = new User("testUser", "testId", UserGameRole.GUESSER);
    ((User) user).setHiddenNumber(hiddenNumber);
  }

  @Test
  void computeCowsAndBulls_shouldReturnRightOneCowsAndOneBulls() {
    var expected = new CowsAndBulls(1, 1);
    var number = 1697;

    var actual = rules.computeCowsAndBulls(number, user);

    assertEquals(expected, actual);
  }

  @Test
  void computeCowsAndBulls_shouldReturnRightFourCowsAndZeroBulls() {
    var expected = new CowsAndBulls(4, 0);
    var number = 5194;

    var actual = rules.computeCowsAndBulls(number, user);

    assertEquals(expected, actual);
  }

  @Test
  void computeCowsAndBulls_shouldReturnRightZeroCowsAndZeroBulls() {
    var expected = new CowsAndBulls();
    var number = 2367;

    var actual = rules.computeCowsAndBulls(number, user);

    assertEquals(expected, actual);
  }

  @Test
  void computeCowsAndBulls_shouldReturnRightZeroCowsAndFourBulls() {
    var expected = new CowsAndBulls(0,4);
    var number = 1549;

    var actual = rules.computeCowsAndBulls(number, user);

    assertEquals(expected, actual);
  }

  @Test
  void computeCowsAndBulls_shouldReturnRightThreeCowsAndOneBulls() {
    var expected = new CowsAndBulls(3, 1);
    var number = 1954;

    var actual = rules.computeCowsAndBulls(number, user);

    assertEquals(expected, actual);
  }

}
