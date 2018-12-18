package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.primitives.CowsAndBulls;
import core.primitives.GameState;
import core.primitives.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateUnitTests {
  private GameState state;

  @BeforeEach
  void setUp() {
    state = new GameState(new CowsAndBulls(0,0), 1234, GameStatus.IN_PROCESS );
  }

  @Test
  void createWithStatus_shouldReturnRightValue(){
    var expected = GameStatus.END_GAME;
    var state = this.state.createWithStatus(GameStatus.END_GAME);

    var actual = state.getStatus();

    assertEquals(expected, actual);
  }

  @Test
  void createWithCowsAndBulls_shouldReturnRightValue(){
    var expected = new CowsAndBulls(2, 1);
    var state = this.state.createWithCowsAndBulls(new CowsAndBulls(2, 1));
    var newStateExpected = new GameState(new CowsAndBulls(2, 1), 1234, GameStatus.IN_PROCESS);

    var actual = state.cowsAndBulls();

    assertEquals(expected, actual);
    assertEquals(state, newStateExpected);
  }


  @Test
  void createWithPossibleNumber_shouldReturnRightValue(){
    var expected = 7832;
    var state = this.state.createWithPossibleNumber(7832);

    var actual = (int)state.possibleNumber();

    assertEquals(expected, actual);
  }

  @Test
  void equals_compareEqualsState_shouldReturnRightValue(){
    var other = new GameState(new CowsAndBulls(), 1234, GameStatus.IN_PROCESS);

    assertTrue(state.equals(other));
  }

  @Test
  void equals_compareWithDifferentCowsAndBulls_shouldReturnRightValue(){
    var other = new GameState(new CowsAndBulls(0, 1), 1234, GameStatus.IN_PROCESS);

    assertFalse(state.equals(other));
  }

  @Test
  void equals_compareWithDifferentPossibleNumber_shouldReturnRightValue(){
    var other = new GameState(new CowsAndBulls(), 1334, GameStatus.IN_PROCESS);

    assertFalse(state.equals(other));
  }

  @Test
  void equals_compareWithDifferentStatus_shouldReturnRightValue(){
    var other = new GameState(new CowsAndBulls(), 1234, GameStatus.END_GAME);

    assertFalse(state.equals(other));
  }

  @Test
  void equals_compareWithNotGameState_shouldReturnRightValue(){
    assertFalse(state.equals(10));
    assertFalse(state.equals("iString"));
    assertFalse(state.equals(new CowsAndBulls()));
  }
}
