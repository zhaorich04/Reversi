package cs3500.reversi.view;

import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents the TextualView for a Reversi Game.
 */
public class ReversiTextualView {
  private final ReadOnlyReversiModel m;


  /**
   * Represents the constructor for a ReversiTextualView.
   * @param m the given model
   */
  public ReversiTextualView(ReadOnlyReversiModel m) {
    this.m = m;
  }

  /**
   * Prints the board.
   * @return the board represented as strings
   */
  public String printBoard() {
    StringBuilder printedBoard = new StringBuilder();
    int size = m.getBoardSize();

    for (int r = -size + 1; r < size; r++) {
      printedBoard.append(" ".repeat(Math.abs(r)));

      for (int q = Math.max(-size + 1, -size + 1 - r); q < Math.min(size, -r + size); q++) {
        printedBoard.append(makeCell(q, r));
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
    StringBuilder printedCell = new StringBuilder();
    printedCell.append(m.getCell(new CellCoordinate(q, r)).toString());
    return printedCell.toString();
  }
}




