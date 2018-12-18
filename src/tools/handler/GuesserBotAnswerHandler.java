package tools.handler;

import static tools.Helper.fromIntegerToList;
import static tools.PossibleNumbersGenerator.generatePossibleNumbers;

import com.google.inject.Inject;
import core.player.IPlayer;
import core.primitives.CowsAndBulls;
import core.primitives.GameState;

import core.primitives.GameStatus;
import core.primitives.HandlerAnswer;
import java.util.Arrays;
import java.util.HashSet;
import tools.Constants;

public class GuesserBotAnswerHandler implements IHandler {

  private HashSet<Integer> possibleNumbers;
  private HashSet<Integer> possibleDigits = new HashSet<>();
  private GameState state;

  @Inject
  public GuesserBotAnswerHandler(GameState state) {
    this.state = state;
    var allDigits = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    possibleDigits.addAll(allDigits);
    this.possibleNumbers = generatePossibleNumbers(possibleDigits);
  }

  @Override
  public HandlerAnswer handleInput(String str, IPlayer user) {
    CowsAndBulls cowsAndBulls;
    try {
      cowsAndBulls = parseCowsAndBulls(str);
    }
    catch (Exception e){
      return new HandlerAnswer(e.getMessage(), null, false);
    }
    state = state.createWithCowsAndBulls(cowsAndBulls);
    handleGameState();
    var endSession = state.getStatus() == GameStatus.END_GAME;
    return new HandlerAnswer(getNextPossibleNumber().toString(), null, endSession);
  }

  private CowsAndBulls parseCowsAndBulls(String userAnswer) throws Exception{
    var cowsAndBulls = userAnswer.split(" ");
    Integer intCows;
    Integer intBulls;
    try {
      intCows = Integer.parseInt(cowsAndBulls[0]);
      intBulls = Integer.parseInt(cowsAndBulls[1]);
    }
    catch (Exception e){
      throw new Exception("Incorrect input");
    }
    return new CowsAndBulls(intCows, intBulls);
  }


  private Integer getNextPossibleNumber() {
    var next = possibleNumbers.iterator().next();
    state = state.createWithPossibleNumber(next);
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
      possibleNumbers.remove(state.possibleNumber());
      return;
    }

    if (state.cowsAndBulls().getBulls().equals(Constants.NUMBER_OF_DIGITS)) {
      state = state.createWithStatus(GameStatus.END_GAME);
      return;
    }

    if(state.cowsAndBulls().getBulls() + state.cowsAndBulls().getCows() == Constants.NUMBER_OF_DIGITS){
      removeUnpossibleDigitsByAllRightDigits(
          new HashSet<Integer>(fromIntegerToList(state.possibleNumber())));
      possibleNumbers = generatePossibleNumbers(possibleDigits);
      possibleNumbers.remove(state.possibleNumber());
      return;
    }

    possibleNumbers.remove(state.possibleNumber());
  }

  private void removeUnpossibleDigitsByAllRightDigits(HashSet<Integer> digits) {
    possibleDigits = digits;
  }
}
