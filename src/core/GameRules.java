package core;


import core.primitives.CowsAndBulls;
import core.player.User;
import tools.Constants;
import tools.Helper;

public class GameRules {

    public CowsAndBulls computeCowsAndBulls(int guessNumber, User user) {
        var cowsAndBulls = new CowsAndBulls();

        var partsOfNumber = Helper.fromIntegerToList(guessNumber);

        for (var i = 0; i < Constants.NUMBER_OF_DIGITS; i++) {
            if (partsOfNumber.get(i).equals(user.getHiddenNumber().get(i))) {
                cowsAndBulls.increaseBulls();
            } else if (user.getHiddenNumber().contains(partsOfNumber.get(i))) {
                cowsAndBulls.increaseCows();
            }
        }
        return cowsAndBulls;
    }
}
