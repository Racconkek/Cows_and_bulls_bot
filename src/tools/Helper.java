package tools;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static List<Integer> fromIntegerToList(Integer number) {
        return number.toString().chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
    }

    public static boolean areThereRepeats(int number) {
        var digits = fromIntegerToList(number);
        return new HashSet<>(digits).size() != Constants.NUMBER_OF_DIGITS;
    }
}
