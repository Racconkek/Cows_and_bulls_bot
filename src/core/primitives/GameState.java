package core.primitives;

public class GameState {

  private final CowsAndBulls cowsAndBulls;
  private final Integer possibleNumber;
  private final GameStatus status;

  public GameState(CowsAndBulls cowsAndBulls, Integer possibleNumber, GameStatus status) {
    this.cowsAndBulls = cowsAndBulls;
    this.possibleNumber = possibleNumber;
    this.status = status;
  }

  public CowsAndBulls cowsAndBulls() {
    return cowsAndBulls;
  }

  public GameState setCowsAndBulls(CowsAndBulls cowsAndBulls) {
    return new GameState(cowsAndBulls, this.possibleNumber, this.status);
  }

  public Integer possibleNumber() {
    return possibleNumber;
  }

  public GameState setPossibleNumber(Integer possibleNumber) {
    return new GameState(this.cowsAndBulls, possibleNumber, this.status);
  }

  public GameStatus getStatus() {
    return status;
  }

  public GameState setStatus(GameStatus status) {
    return new GameState(this.cowsAndBulls, this.possibleNumber, status);
  }
}
