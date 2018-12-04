package tools;

import java.util.ArrayList;
import java.util.Random;

public class HiddenNumberGenerator {

    public static ArrayList<Integer> createHiddenNumber() {
        ArrayList<Integer> res = new ArrayList<>();
        Random random = new Random();
        res.add(random.nextInt(9) + 1);
        while (res.size() < Constants.NUMBER_OF_DIGITS) {
            int newNumber = (random.nextInt(10));
            if (!res.contains(newNumber)) {
                res.add(newNumber);
            }
        }
        return res;
    }
}
