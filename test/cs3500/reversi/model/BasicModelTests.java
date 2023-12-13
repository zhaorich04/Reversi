package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import cs3500.reversi.strategy.AvoidNeighborCornerStrategy;
import cs3500.reversi.strategy.CaptureCornersStrategy;
import cs3500.reversi.strategy.MaxCaptureStrategy;
import cs3500.reversi.view.ReversiTextualView;

/**
 * Test class for the model's inherited methods and its constructor. Tests that the basic
 * player actions are caught when invalid, and that the correct winner is chosen.
 *
 */
public class BasicModelTests {

  BasicModel m;

  @Before
  public void init() {
    m = new BasicModel(4);
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
            m.makeMove(m.getCell(new CellCoordinate(2, -1))));
  }

  @Test
  public void testMakeMoveCellOccupied() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            m.makeMove(m.getCell(new CellCoordinate(1, 0))));
  }

  @Test
  public void testPassTurnGameOver() {
    m.passTurn();
    m.passTurn();
    Assert.assertThrows(IllegalStateException.class, () -> m.passTurn());
  }

  @Test
  public void testGetWinnerBlack() {
    m.makeMove(m.getCell(new CellCoordinate(2, -1)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getWinner(), Disk.BLACK);
  }

  @Test
  public void testGetWinnerWhite() {
    m.passTurn();
    m.makeMove(m.getCell(new CellCoordinate(2, -1)));
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
    m.makeMove(m.getCell(new CellCoordinate(2, -1)));
    m.passTurn();
    m.passTurn();
    Assert.assertEquals(m.getBlackScore(), 5);
    Assert.assertEquals(m.getWhiteScore(), 2);

  }

  // tests that flipping in two directions still works for updating scores
  @Test
  public void testGetScoresComplex() {
    ReversiTextualView view = new ReversiTextualView(m);
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    m.makeMove(new Cell(new CellCoordinate(1, -3)));
    m.makeMove(new Cell(new CellCoordinate(-2, 1)));
    m.makeMove(new Cell(new CellCoordinate(-1, -1)));
    m.makeMove(new Cell(new CellCoordinate(2, -3)));
    m.makeMove(new Cell(new CellCoordinate(-1, 2)));
    Assert.assertEquals(m.getBlackScore(), 4);
    Assert.assertEquals(m.getWhiteScore(), 8);
  }

  @Test
  public void test() {
    ArrayList<Cell> list = new ArrayList<>();
    list.add(m.getCell(new CellCoordinate(2, -3)));
    list.add(m.getCell(new CellCoordinate(3, -2)));
    list.add(m.getCell(new CellCoordinate(2, -2)));
    Assert.assertEquals(list, m.getCell(new CellCoordinate(3, -3)).neighborsList(m.getGameBoard()));
  }

  @Test
  public void testMaxCapture() {
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    m.makeMove(new Cell(new CellCoordinate(1,-2)));
    Assert.assertEquals(m.getCell(new CellCoordinate(1,-3)), strategy.chooseCell(m));
  }

  @Test
  public void testMaxCapture2() {
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    m.makeMove(new Cell(new CellCoordinate(1,-2)));
    m.makeMove(new Cell(new CellCoordinate(1,-3)));
    m.makeMove(new Cell(new CellCoordinate(2,-1)));
    Assert.assertEquals(m.getCell(new CellCoordinate(1,1)), strategy.chooseCell(m));
  }

  @Test
  public void testMaxCapture3() {
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    CellCoordinate coord = strategy.chooseCell(m).getCoord();
    System.out.println(coord.getQ());
    System.out.println(coord.getR());
    Assert.assertEquals(new CellCoordinate(1, -2), coord);
  }

  @Test
  public void testAvoidCornerNeighbor() {
    AvoidNeighborCornerStrategy strategy = new AvoidNeighborCornerStrategy();
    m.makeMove(new Cell(new CellCoordinate(1,-2)));
    m.makeMove(new Cell(new CellCoordinate(1,-3)));
    m.makeMove(new Cell(new CellCoordinate(2,-1)));
    m.makeMove(new Cell(new CellCoordinate(1,1)));
    m.makeMove(new Cell(new CellCoordinate(-1,2)));
    m.makeMove(new Cell(new CellCoordinate(-1,1)));
    m.makeMove(new Cell(new CellCoordinate(2,-3)));
    Assert.assertEquals(m.getCell(new CellCoordinate(-1,-1)), strategy.chooseCell(m));
  }

  @Test
  public void testCaptureCorners() {
    CaptureCornersStrategy strategy = new CaptureCornersStrategy();
    m.makeMove(new Cell(new CellCoordinate(1,-2)));
    m.makeMove(new Cell(new CellCoordinate(1,-3)));
    m.makeMove(new Cell(new CellCoordinate(2,-1)));
    m.makeMove(new Cell(new CellCoordinate(1,1)));
    m.makeMove(new Cell(new CellCoordinate(-1,2)));
    m.makeMove(new Cell(new CellCoordinate(-1,1)));
    m.makeMove(new Cell(new CellCoordinate(2,-3)));
    m.makeMove(new Cell(new CellCoordinate(-1,-1)));
    m.makeMove(new Cell(new CellCoordinate(-1,-2)));
    m.makeMove(new Cell(new CellCoordinate(-2,-1)));
    m.makeMove(new Cell(new CellCoordinate(2,1)));
    m.makeMove(new Cell(new CellCoordinate(1,2)));
    m.makeMove(new Cell(new CellCoordinate(-3,0)));
    m.makeMove(new Cell(new CellCoordinate(-1,3)));
    m.makeMove(new Cell(new CellCoordinate(-2,1)));
    Assert.assertEquals(m.getCell(new CellCoordinate(3,0)), strategy.chooseCell(m));
  }

  @Test
  public void testCopyBoard() {
    // Test that copyBoard creates a deep copy of the gameBoard
    m.makeMove(new Cell(new CellCoordinate(1, -2)));
    Map<CellCoordinate, Cell> copiedBoard = m.copyBoard();
    Assert.assertNotSame(m.getGameBoard(), copiedBoard);
    Assert.assertEquals(m.getGameBoard(), copiedBoard);
  }

  @Test
  public void testIsMoveLegal() {
    // Test a legal move
    Cell legalMoveCell = new Cell(new CellCoordinate(1, -2));
    Assert.assertTrue(m.isMoveLegal(legalMoveCell));

    // Test an illegal move (occupied cell)
    Cell illegalMoveCell = new Cell(new CellCoordinate(1, 0));
    Assert.assertFalse(m.isMoveLegal(illegalMoveCell));

    // Test an illegal move (out of bounds)
    Cell outOfBoundsMoveCell = new Cell(new CellCoordinate(5, 5));
    Assert.assertFalse(m.isMoveLegal(outOfBoundsMoveCell));
  }

  @Test
  public void testHasLegalMove() {
    // Test when there is a legal move
    Assert.assertTrue(m.hasLegalMove());

    // Test when there is no legal move
    m.passTurn();
    m.passTurn();
    Assert.assertFalse(m.hasLegalMove());
  }
}
