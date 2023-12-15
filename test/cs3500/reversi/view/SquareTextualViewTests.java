package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.SquareReversiModel;

/**
 * Test class for the square reversi view.
 */
public class SquareTextualViewTests {
  @Test
  public void testInitialBoard() {
    SquareReversiModel m = new SquareReversiModel(8);
    m.startGame();
    SquareReversiTextualView view = new SquareReversiTextualView(m);
    Assert.assertEquals(view.printBoard(),
            "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ O X _ _ _ \n"
                    + "_ _ _ X O _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n");
  }

  @Test
  public void testOneMove() {
    SquareReversiModel m = new SquareReversiModel(8);
    m.startGame();
    m.makeMove(new Cell(new CellCoordinate(4,2)));
    SquareReversiTextualView view = new SquareReversiTextualView(m);
    Assert.assertEquals(view.printBoard(),
            "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ O _ _ _ \n"
                    + "_ _ _ O O _ _ _ \n"
                    + "_ _ _ X O _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n");
  }

  @Test
  public void testTwoWayFlip() {
    SquareReversiModel m = new SquareReversiModel(8);
    m.startGame();
    m.makeMove(new Cell(new CellCoordinate(4,2)));
    m.makeMove(new Cell(new CellCoordinate(5,2)));
    m.makeMove(new Cell(new CellCoordinate(5,3)));
    m.makeMove(new Cell(new CellCoordinate(5,4)));
    SquareReversiTextualView view = new SquareReversiTextualView(m);
    Assert.assertEquals(view.printBoard(),
            "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ O X _ _ \n"
                    + "_ _ _ O O X _ _ \n"
                    + "_ _ _ X X X _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n"
                    + "_ _ _ _ _ _ _ _ \n");
  }

}
