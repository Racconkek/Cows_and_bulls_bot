package tools;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static List<Integer> fromIntegerToList(Integer number) {
        return number.toString().chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
    }
}
