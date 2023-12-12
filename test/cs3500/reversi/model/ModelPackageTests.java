package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CellCoordinate;

/**
 * Test class for the non-inherited methods of the model.
 */
public class ModelPackageTests {

  CellCoordinate cc;
  Cell cell;

  @Before
  public void init() {
    cc = new CellCoordinate(0, 0);
    cell = new Cell(cc);
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(cc, new CellCoordinate(0, 0));
  }

  @Test
  public void testCellToString() {
    Assert.assertEquals(cell.toString(), "_ ");
  }
}

