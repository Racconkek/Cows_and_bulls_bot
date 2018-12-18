package tests.core;

import core.player.User;
import core.primitives.UserGameRole;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserUnitTests {

  @Test
  void getRole_gettingRoleRiddlerUser_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.RIDDLER);
    var expected = UserGameRole.RIDDLER;

    var actual = user.getRole();

    assertEquals(expected, actual);
  }

  @Test
  void getRole_gettingRoleGuesserUser_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = UserGameRole.GUESSER;

    var actual = user.getRole();

    assertEquals(expected, actual);
  }


  @Test
  void setRole_shouldSettingRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    user.setRole(UserGameRole.WAITER);
    var expected = UserGameRole.WAITER;

    var actual = user.getRole();

    assertEquals(expected, actual);
  }

  @Test
  void getName_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = "testName";

    var actual = user.getName();

    assertEquals(expected, actual);
  }

  @Test
  void getChatID_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = "IChatId";

    var actual = user.getChatID();

    assertEquals(expected, actual);
  }

  @Test
  void getTries_gettingStartTries_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = 0;

    var actual = user.getTries();

    assertEquals(expected, (int) actual);
  }

  @Test
  void getTries_gettingTriesAfterStepUser_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = new Random().nextInt(15);
    for (var i = 0; i < expected; i++) {
      user.increaseTries();
    }

    var actual = user.getTries();

    assertEquals(expected, (int) actual);
  }

  @Test
  void increaseTries_shouldReturnRightValue() {
    var user = new User("testName", "IChatId", UserGameRole.GUESSER);
    var expected = new Random().nextInt(15);
    for (var i = 0; i < expected; i++) {
      user.increaseTries();
    }

    var actual = user.getTries();

    assertEquals(expected, (int) actual);
  }
}
