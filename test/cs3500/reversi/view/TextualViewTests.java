package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicModel;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.view.ReversiTextualView;

/**
 * Represents tests for the TextualView.
 */
public class TextualViewTests {
  @Test
  public void testInitialBoard() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    Assert.assertEquals(view.printBoard(),
            "   _ _ _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n");
  }

  @Test
  public void testMinBoardSize() {
    BasicModel m = new BasicModel(3);
    ReversiTextualView view = new ReversiTextualView(m);
    Assert.assertEquals(view.printBoard(),
            "  _ _ _ \n"
                    + " _ X O _ \n"
                    + "_ O _ X _ \n"
                    + " _ X O _ \n"
                    + "  _ _ _ \n");
  }

  @Test
  public void testValidBlackMove() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    Assert.assertEquals(view.printBoard(),
            "   _ _ _ _ \n"
                    + "  _ _ O _ _ \n"
                    + " _ _ O O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n");
  }

  @Test
  public void testOutOfBoundsBlackMove() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    m.makeMove(new Cell(new CellCoordinate(1, -3)));
    Assert.assertEquals(view.printBoard(),
            "   _ X _ _ \n"
                    + "  _ _ X _ _ \n"
                    + " _ _ O X _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n");
    Assert.assertThrows(IllegalArgumentException.class, () ->
            m.makeMove(new Cell(new CellCoordinate(1, -4))));
  }

  @Test
  public void testNoAdjacentNeighbors() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            m.makeMove(new Cell(new CellCoordinate(3, 0))));
    Assert.assertEquals(view.printBoard(),
            "   _ _ _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n");
  }

  @Test
  public void testBlackFlipsNoTiles() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    m.makeMove(new Cell(new CellCoordinate(1, -3)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            m.makeMove(new Cell(new CellCoordinate(2, -2))));
    Assert.assertEquals(view.printBoard(),
            "   _ X _ _ \n"
                    + "  _ _ X _ _ \n"
                    + " _ _ O X _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + "   _ _ _ _ \n");
  }

  @Test
  public void testFlipTwoDirections() {
    BasicModel m = new BasicModel(4);
    ReversiTextualView view = new ReversiTextualView(m);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    m.makeMove(new Cell(new CellCoordinate(1, -3)));
    m.makeMove(new Cell(new CellCoordinate(-2, 1)));
    m.makeMove(new Cell(new CellCoordinate(-1, -1)));
    m.makeMove(new Cell(new CellCoordinate(2, -3)));
    m.makeMove(new Cell(new CellCoordinate(-1, 2)));
    Assert.assertEquals(view.printBoard(),
            "   _ X O _ \n"
            + "  _ _ O _ _ \n"
            + " _ X O X _ _ \n"
            + "_ _ X _ X _ _ \n"
            + " _ O X X _ _ \n"
            + "  _ _ X _ _ \n"
            + "   _ _ _ _ \n");
  }
}
