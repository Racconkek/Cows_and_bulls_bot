package core;

import static tools.Helper.fromIntegerToList;
import static tools.PossibleNumbersGenerator.generatePossibleNumbers;

import core.player.IPlayer;
import core.primitives.CowsAndBulls;
import core.primitives.GameState;

import core.primitives.GameStatus;
import core.primitives.HandlerAnswer;
import java.util.Arrays;
import java.util.HashSet;
import tools.Constants;
import tools.handler.IHandler;

public class Algo implements IHandler {

  private HashSet<Integer> possibleNumbers;
  private HashSet<Integer> possibleDigits = new HashSet<>();
  private GameState state;

  public Algo(GameState state) {
    this.state = state;
    var allDigits = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    possibleDigits.addAll(allDigits);
    this.possibleNumbers = generatePossibleNumbers(possibleDigits);
  }

  @Override
  public HandlerAnswer handleInput(String str, IPlayer user) {
    var cowsAndBulls = parseCowsAndBulls(str);
    state = state.setCowsAndBulls(cowsAndBulls);
    handleGameState();
    return null;
  }

  private CowsAndBulls parseCowsAndBulls(String userAnswer) {
    var cowsAndBulls = userAnswer.split(" ");
    return new CowsAndBulls(Integer.parseInt(cowsAndBulls[0]), Integer.parseInt(cowsAndBulls[1]));
  }


  private Integer getNextPossibleNumber() {
    var next = possibleNumbers.stream().findFirst().get();
    state =state.setPossibleNumber(next);
    return next;
  }

  private void handleGameState() {
    if (state.cowsAndBulls().getCows() == 0 && state.cowsAndBulls().getBulls() == 0) {
      var unpossibleDigits = new HashSet<Integer>(fromIntegerToList(state.possibleNumber()));
      possibleDigits.removeAll(unpossibleDigits);
      possibleNumbers = generatePossibleNumbers(possibleDigits);
      return;
    }

    if (state.cowsAndBulls().getCows().equals(Constants.NUMBER_OF_DIGITS)) {
      removeUnpossibleDigitsByAllRightDigits(
          new HashSet<Integer>(fromIntegerToList(state.possibleNumber())));
      possibleNumbers = generatePossibleNumbers(possibleDigits);
      return;
    }

    if (state.cowsAndBulls().getBulls().equals(Constants.NUMBER_OF_DIGITS)) {
      state = state.setStatus(GameStatus.END_GAME);
      return;
    }

    possibleNumbers.remove(state.possibleNumber());
  }

  private void removeUnpossibleDigitsByAllRightDigits(HashSet<Integer> digits) {
    possibleDigits = digits;
  }
}
