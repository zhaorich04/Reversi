package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test to run an example game of Reversi.
 */
public class ModelGameExampleTest {

  @Test
  public void test() {
    BasicModel m = new BasicModel(3);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    m.makeMove(new Cell(new CellCoordinate(-1, 2)));
    m.makeMove(new Cell(new CellCoordinate(1, 1)));
    m.makeMove(new Cell(new CellCoordinate(-1, -1)));
    m.makeMove(new Cell(new CellCoordinate(-2, 1)));
    m.makeMove(new Cell(new CellCoordinate(2, -1)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getWinner(), Disk.WHITE);

  }
}

