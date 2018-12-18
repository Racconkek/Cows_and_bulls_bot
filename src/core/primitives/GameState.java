package core.primitives;

import com.google.inject.Inject;
import core.player.User;

public class GameState {

  private final CowsAndBulls cowsAndBulls;
  private final Integer possibleNumber;
  private final GameStatus status;

  @Inject
  public GameState(){
    this.cowsAndBulls = new CowsAndBulls();
    this.possibleNumber = 1234;
    this.status = GameStatus.IN_PROCESS;
  }

  public GameState(CowsAndBulls cowsAndBulls, Integer possibleNumber, GameStatus status) {
    this.cowsAndBulls = cowsAndBulls;
    this.possibleNumber = possibleNumber;
    this.status = status;
  }

  public CowsAndBulls cowsAndBulls() {
    return cowsAndBulls;
  }

  public GameState createWithCowsAndBulls(CowsAndBulls cowsAndBulls) {
    return new GameState(cowsAndBulls, this.possibleNumber, this.status);
  }

  public Integer possibleNumber() {
    return possibleNumber;
  }

  public GameState createWithPossibleNumber(Integer possibleNumber) {
    return new GameState(this.cowsAndBulls, possibleNumber, this.status);
  }

  public GameStatus getStatus() {
    return status;
  }

  public GameState createWithStatus(GameStatus status) {
    return new GameState(this.cowsAndBulls, this.possibleNumber, status);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof GameState) {
      var other = (GameState) obj;
      return this.cowsAndBulls.equals(other.cowsAndBulls) && this.status.equals(other.status) &&
          this.possibleNumber.equals(other.possibleNumber);
    }
    return false;
  }
}
