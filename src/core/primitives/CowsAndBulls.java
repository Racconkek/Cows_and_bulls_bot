package core.primitives;

public class CowsAndBulls {

  private Integer cows;
  private Integer bulls;


  public CowsAndBulls(Integer cows, Integer bulls) {
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

  public void increaseCows()  { cows++; }

  public void increaseBulls()  { bulls++; }
}
