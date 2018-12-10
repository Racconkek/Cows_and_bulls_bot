package tools.handler;

import com.google.inject.Inject;
import core.GameRules;
import core.player.IPlayer;
import core.primitives.HandlerAnswer;
import exceptions.WrongInputException;
import tools.Constants;
import tools.Helper;
import java.util.HashSet;

public class BotAnswerHandler implements IHandler{
    private final GameRules rules;

    @Inject
    public BotAnswerHandler(GameRules rules) {
        this.rules = rules;
    }

    public HandlerAnswer handleInput(String str, IPlayer user) {
        int guess;
        try {
            guess = parseInput(str, user);
        } catch (WrongInputException e) {
            return new HandlerAnswer(e.getMessage(), false);
        } catch (NumberFormatException e) {
            return new HandlerAnswer("Incorrect Input", false);
        }
        return makeAnswer(user, guess);
    }

    private Integer parseInput(String str, IPlayer user) throws WrongInputException {
        int input = Integer.parseInt(str);

        if (Integer.toString(input).length() != Constants.NUMBER_OF_DIGITS) {
            throw new WrongInputException("Wrong count of digits");
        }
        else if (areThereRepeats(input)) {
            throw new WrongInputException("There are repetitions in number");
        }

        user.increaseTries();
        return input;
    }

    private HandlerAnswer makeAnswer(IPlayer user, Integer guess) {
        var cowsAndBulls = rules.computeCowsAndBulls(guess, user);

        if (cowsAndBulls.getBulls().equals(Constants.NUMBER_OF_DIGITS)) {
            return new HandlerAnswer(String.format(
                    "Congratulations! You win! \nAmount of tries %d \n" + user.getStringCowsAndBullsNumber(),
                    user.getTries()), true);
        } else {
            return new HandlerAnswer(String.format("Cows: %d, Bulls: %d.",
                    cowsAndBulls.getCows(), cowsAndBulls.getBulls()), false);
        }
    }

    private boolean areThereRepeats(int number) {
        var digits = Helper.fromIntegerToList(number);
        return new HashSet<>(digits).size() != Constants.NUMBER_OF_DIGITS;
    }
}