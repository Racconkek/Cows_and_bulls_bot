package tests;

import static tools.Helper.fromIntegerToList;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelperUnitTests {

  @Test
  void fromIntegerToList_shouldReturnRightValue() {
    var expected = List.of(1, 2, 6, 3);
    var number = 1263;

    var actual = fromIntegerToList(number);

    assertEquals(expected, actual);
  }

  @Test
  void fromIntegerToList_shouldRetxurnRightValue() {
    var expected = List.of(2, 6, 7, 9, 2);
    var number = 26792;

    var actual = fromIntegerToList(number);

    assertEquals(expected, actual);
  }
}
