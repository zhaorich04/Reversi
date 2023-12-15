package cs3500.reversi.view;

import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a textual view for a square reversi game.
 */
public class SquareReversiTextualView {
  private final ReadOnlyReversiModel m;


  /**
   * Represents the constructor for a ReversiTextualView.
   * @param m the given model
   */
  public SquareReversiTextualView(ReadOnlyReversiModel m) {
    this.m = m;
  }

  /**
   * Prints the board.
   * @return the board represented as strings
   */
  public String printBoard() {
    StringBuilder printedBoard = new StringBuilder();
    int size = m.getBoardSize();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        printedBoard.append(makeCell(col, row));
      }
      printedBoard.append("\n");
    }
    return printedBoard.toString();
  }

  /**
   * Prints an individual cell.
   * @param q column of the cell
   * @param r row of the cell
   * @return the cell represented as a string
   */
  private String makeCell(int q, int r) {
    System.out.println(m.getCell(new CellCoordinate(1,6)));
    return m.getCell(new CellCoordinate(q, r)).toString();
  }
}
