package tests.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.PossibleNumbersGenerator.generatePossibleNumbers;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class PossibleNumberGeneratorUnitTests {

  @Test
  void generatePossibleNumbers_shouldReturnRightValue() {
    var allDigits = Arrays.asList(0, 1, 2, 3);
    var possibleDigits = new HashSet<Integer>(allDigits);


    var allNumbers = Arrays
        .asList(1230, 1203, 1032, 1023, 1302, 1320, 2130, 2103, 2031, 2013, 2301, 2310, 3210, 3201,
            3012, 3021, 3102, 3120);
    var expected = new HashSet<Integer>(allNumbers);

    var actual = generatePossibleNumbers(possibleDigits);

    assertEquals(expected, actual);
  }

}
