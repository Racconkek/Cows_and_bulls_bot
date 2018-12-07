package tools;

import com.google.inject.Inject;
import core.GameRules;
import core.HighScoresMaker;
import core.player.User;
import exceptions.WrongInputException;
import java.util.HashSet;

public class Handler implements IHandler{
    private final GameRules rules;
    private final HighScoresMaker highScoresMaker;

    @Inject
    public Handler(GameRules rules, HighScoresMaker highScoresMaker) {
        this.rules = rules;
        this.highScoresMaker = highScoresMaker;
    }

    public String handleInput(String str, User user) {
        int guess;
        try {
            guess = parseInput(str, user);
        } catch (WrongInputException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Incorrect Input";
        }
        return makeAnswer(user, guess);
    }

    private Integer parseInput(String str, User user) throws WrongInputException {
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

    private String makeAnswer(User user, Integer guess) {
        var cowsAndBulls = rules.computeCowsAndBulls(guess, user);

        if (cowsAndBulls.getBulls().equals(Constants.NUMBER_OF_DIGITS)) {
            highScoresMaker.saveHighScore(user);
            return (String.format(
                    "Congratulations! You win! \nAmount of tries %d \n" + user.getStringCowsAndBullsNumber(),
                    user.getTries()));
        } else {
            return (String.format("Cows: %d, Bulls: %d.", cowsAndBulls.getCows(), cowsAndBulls.getBulls()));
        }
    }

    private boolean areThereRepeats(int number) {
        var digits = Helper.fromIntegerToList(number);
        return new HashSet<>(digits).size() != Constants.NUMBER_OF_DIGITS;
    }
}
