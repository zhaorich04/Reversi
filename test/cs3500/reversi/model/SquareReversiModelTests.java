package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareReversiModelTests {
  SquareReversiModel m;

  @Before
  public void init() {
    m = new SquareReversiModel(8);
    m.startGame();
  }

  @Test
  public void testIllegalConstructor() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new BasicModel(2)
    );
  }

  @Test
  public void testMakeMoveGameOver() {
    m.passTurn();
    m.passTurn();
    Assert.assertThrows(IllegalStateException.class, () ->
            m.makeMove(m.getCell(new CellCoordinate(0, 0))));
  }

  @Test
  public void testMakeMoveCellOccupied() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            m.makeMove(m.getCell(new CellCoordinate(3, 3))));
  }

  @Test
  public void testPassTurnGameOver() {
    m.passTurn();
    m.passTurn();
    Assert.assertThrows(IllegalStateException.class, () -> m.passTurn());
  }

  @Test
  public void testGetWinnerBlack() {
    m.makeMove(m.getCell(new CellCoordinate(4, 2)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getWinner(), Disk.BLACK);
  }

  @Test
  public void testGetWinnerWhite() {
    m.passTurn();
    m.makeMove(m.getCell(new CellCoordinate(3, 2)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getWinner(), Disk.WHITE);
  }

  @Test
  public void testGetWinnerDraw() {
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getWinner(), Disk.EMPTY);
  }

  @Test
  public void testGetScoresSimple() {
    m.makeMove(m.getCell(new CellCoordinate(4, 2)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getBlackScore(), 4);
    Assert.assertEquals(m.getWhiteScore(), 1);
  }

  @Test
  public void testGetScoresComplex() {
    m.makeMove(new Cell(new CellCoordinate(4,2)));
    m.makeMove(new Cell(new CellCoordinate(5,2)));
    m.makeMove(new Cell(new CellCoordinate(5,3)));
    m.makeMove(new Cell(new CellCoordinate(5,4)));
    Assert.assertEquals(m.getBlackScore(), 3);
    Assert.assertEquals(m.getWhiteScore(), 5);
  }
}
