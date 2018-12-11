package tools;

import static tools.Helper.areThereRepeats;
import static tools.Helper.fromIntegerToList;

import java.util.HashSet;
import java.util.Set;

public class PossibleNumbersGenerator {

  public static HashSet<Integer> generatePossibleNumbers(Set<Integer> possibleDigits){
    var minNumber = 10*(Constants.NUMBER_OF_DIGITS-1);
    var maxNumber = 10*(Constants.NUMBER_OF_DIGITS)-1;
    var possibleNumbers = new HashSet<Integer>();

    for (var i = minNumber; i <= maxNumber; i++){
      if(areThereRepeats(i))
        continue;

      if(!possibleDigits.containsAll(fromIntegerToList(i)))
        continue;

      possibleNumbers.add(i);
    }

    return possibleNumbers;
  }
}
