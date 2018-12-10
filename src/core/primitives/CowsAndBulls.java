package core.primitives;

import tools.Constants;

public class CowsAndBulls {

  private Integer cows;
  private Integer bulls;


  public CowsAndBulls(Integer cows, Integer bulls) {
    if (bulls > Constants.NUMBER_OF_DIGITS || cows > Constants.NUMBER_OF_DIGITS) {
      throw new IllegalArgumentException("Out of range bulls");
    }

    if (cows + bulls > Constants.NUMBER_OF_DIGITS) {
      throw new IllegalArgumentException("Out of range cows and bulls");
    }

    this.cows = cows;
    this.bulls = bulls;
  }

  public CowsAndBulls() {
    this.cows = 0;
    this.bulls = 0;
  }

  public Integer getCows() {
    return cows;
  }

  public Integer getBulls() {
    return bulls;
  }

  public void increaseCows() {
    if (cows + 1 > Constants.NUMBER_OF_DIGITS) {
      throw new IllegalStateException("Cows more than digits from number");
    }
    cows++;
  }

  public void increaseBulls() {
    if (bulls + 1 > Constants.NUMBER_OF_DIGITS) {
      throw new IllegalStateException("Bulls more than digits from number");
    }
    bulls++;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CowsAndBulls) {
      var other = (CowsAndBulls) obj;
      return this.cows.equals(other.cows) && this.bulls.equals(other.bulls);
    }
    return false;
  }

}
