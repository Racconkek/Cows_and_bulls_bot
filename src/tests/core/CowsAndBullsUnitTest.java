package tests.core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.primitives.CommandResult;
import core.primitives.CowsAndBulls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.Constants;

class CowsAndBullsUnitTest {

  private CowsAndBulls cowsAndBulls;

  @BeforeEach
  void setUp() {
    cowsAndBulls = new CowsAndBulls(0, 1);
  }

  @Test
  void getCows_shouldReturnRightValue(){
    var expected = 0;

    var actual = (int)cowsAndBulls.getCows();

    assertEquals(expected, actual);
  }

  @Test
  void getCows_gettingAfterIncrease_shouldReturnRightValue(){
    var expected = 2;
    for (var i = 0; i < 2; i++)
      cowsAndBulls.increaseCows();

    var actual = (int)cowsAndBulls.getCows();

    assertEquals(expected, actual);
  }

  @Test
  void getBulls_shouldReturnRightValue(){
    var expected = 0;

    var actual = (int)cowsAndBulls.getCows();

    assertEquals(expected, actual);
  }

  @Test
  void getBulls_gettingAfterIncrease_shouldReturnRightValue(){
    var expected = 2;
    cowsAndBulls.increaseBulls();

    var actual = (int)cowsAndBulls.getBulls();

    assertEquals(expected, actual);
  }

  @Test
  void increaseCows_shouldReturnRightValue(){
    var expected =  4;
    while(Constants.NUMBER_OF_DIGITS - cowsAndBulls.getBulls() - cowsAndBulls.getCows() > 0)
      cowsAndBulls.increaseBulls();

    var actual = (int)cowsAndBulls.getBulls();
    assertEquals(expected, actual);
  }


  @Test
  void increaseCows_outOfRange_shouldThrowException(){
    cowsAndBulls.increaseCows();
    cowsAndBulls.increaseCows();
    cowsAndBulls.increaseCows();

    assertThrows(IllegalStateException.class, () -> cowsAndBulls.increaseCows());
  }

  @Test
  void increaseBulls_shouldReturnRightValue(){
    var expected =  4;
    while(Constants.NUMBER_OF_DIGITS - cowsAndBulls.getBulls() - cowsAndBulls.getCows() > 0)
      cowsAndBulls.increaseBulls();

    var actual = (int)cowsAndBulls.getBulls();
    assertEquals(expected, actual);
  }

  @Test
  void increaseBulls_outOfRange_shouldThrowException(){
    cowsAndBulls.increaseBulls();
    cowsAndBulls.increaseBulls();
    cowsAndBulls.increaseBulls();

    assertThrows(IllegalStateException.class, () -> cowsAndBulls.increaseBulls());
  }

  @Test
  void CowsAndBulls_outOfRangeCows_shouldThrowException(){
    assertThrows(IllegalArgumentException.class, ()-> new CowsAndBulls(5,0));
  }

  @Test
  void CowsAndBulls_outOfRangeBulls_shouldThrowException(){
    assertThrows(IllegalArgumentException.class, ()-> new CowsAndBulls(0,9));
  }

  @Test
  void CowsAndBulls_outOfRangeCowsAndBulls_shouldThrowException(){
    assertThrows(IllegalArgumentException.class, ()-> new CowsAndBulls(2,3));
  }

  @Test
  void equals_shouldReturnFalse(){
    assertFalse(cowsAndBulls.equals(10));
    assertFalse(cowsAndBulls.equals("iString"));
    assertFalse(cowsAndBulls.equals(new CommandResult("f", "s")));
    assertFalse(cowsAndBulls.equals(null));
  }

  @Test
  void equals_shouldReturnTrue(){
    assertTrue(cowsAndBulls.equals(new CowsAndBulls(0, 1)));
  }
}
