package tools.handler;

import static tools.Helper.areThereRepeats;

import com.google.inject.Inject;
import core.GameRules;
import core.player.IPlayer;
import core.primitives.HandlerAnswer;
import exceptions.WrongInputException;
import tools.Constants;

public class RiddleBotAnswerHandler implements IHandler{
    private final GameRules rules;

    @Inject
    public RiddleBotAnswerHandler(GameRules rules) {
        this.rules = rules;
    }

    public HandlerAnswer handleInput(String str, IPlayer user) {
        int guess;
        try {
            guess = parseInput(str, user);
        } catch (WrongInputException e) {
            return new HandlerAnswer(e.getMessage(),null, false);
        } catch (NumberFormatException e) {
            return new HandlerAnswer("Incorrect Input", null, false);
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
                    user.getTries()), null,true);
        } else {
            return new HandlerAnswer(String.format("Cows: %d, Bulls: %d.",
                    cowsAndBulls.getCows(), cowsAndBulls.getBulls()), null, false);
        }
    }
}
