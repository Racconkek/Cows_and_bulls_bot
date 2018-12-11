package core;

import static tools.Helper.fromIntegerToList;
import static tools.PossibleNumbersGenerator.generatePossibleNumbers;

import core.primitives.CowsAndBulls;
import core.primitives.GameState;
import java.util.Arrays;
import java.util.HashSet;

public class Algo {
  private HashSet<Integer> possibleNumbers;
  private HashSet<Integer> possibleDigits = new HashSet<>();
  private GameState state;

  public Algo(GameState state) {
    this.state = state;
    var allDigits = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    possibleDigits.addAll(allDigits);
    this.possibleNumbers = generatePossibleNumbers(possibleDigits);
  }

  public void setState(GameState state) {this.state = state;}

  public void handleGameState(){
    if(state.cowsAndBulls().getCows() == 0 && state.cowsAndBulls().getBulls() == 0)
      possibleDigits.removeAll(fromIntegerToList(state.possibleNumber()));

//    if()

  }

}
