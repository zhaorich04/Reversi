package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the square reversi model.
 */
public class SquareModelTests {

  SquareReversiModel m;

  @Before
  public void init() {
    m = new SquareReversiModel(8);
    m.startGame();
  }

  @Test
  public void test() {
    Assert.assertEquals(m.getCell(new CellCoordinate(-1, -1)).getDisk(), Disk.BLACK);
  }


}
