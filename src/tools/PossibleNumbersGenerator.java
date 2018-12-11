package tools;

import static tools.Helper.areThereRepeats;
import static tools.Helper.fromIntegerToList;

import java.util.HashSet;
import java.util.Set;

public class PossibleNumbersGenerator {

  public static HashSet<Integer> generatePossibleNumbers(Set<Integer> possibleDigits){
    var minNumber = (int)Math.pow(10,Constants.NUMBER_OF_DIGITS - 1);
    var maxNumber = Math.pow(10,Constants.NUMBER_OF_DIGITS)-1;
    var possibleNumbers = new HashSet<Integer>();

    outerloop:
    for (var i = minNumber; i <= maxNumber; i++){
      if(areThereRepeats(i))
        continue;

      for(var k = 0; k< Constants.NUMBER_OF_DIGITS; k++){
        if(!possibleDigits.contains(fromIntegerToList(i).get(k))) {
        continue outerloop;
        }
      }


      possibleNumbers.add(i);
    }

    return possibleNumbers;
  }
}
