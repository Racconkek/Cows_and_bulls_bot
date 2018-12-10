package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
  void increaseBulls_shouldReturnRightValue(){
    var expected =  4;
    while(Constants.NUMBER_OF_DIGITS - cowsAndBulls.getBulls() - cowsAndBulls.getCows() > 0)
      cowsAndBulls.increaseBulls();

    var actual = (int)cowsAndBulls.getBulls();
    assertEquals(expected, actual);
  }
}
