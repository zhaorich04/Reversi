package cs3500.reversi.model;

import java.util.Map;

/**
 * Interface for the observation methods for reversi.
 */
public interface ReadOnlyReversiModel {

  /**
   * Returns whether the current player has a legal move.
   * @return true if the current player has a legal move.
   */
  public boolean hasLegalMove();

  /**
   * Retrieves the disk of the winning player.
   * @return Disk of the winning player
   */
  public Disk getWinner();

  public int getBoardSize();

  /**
   * Retrieves a cell given a CellCoordinate.
   * @param coordinate given CellCoordinate
   * @return the Cell at the given CellCoordinate
   */
  public Cell getCell(CellCoordinate coordinate);


  /**
   * Determines if placing a given cell is a valid move.
   * @param currentCell given cell
   * @return true if the move is legal
   */
  public boolean isMoveLegal(Cell currentCell);

  /**
   * Determines the current score for white. The score is the number of white disks on the board.
   * @return white's score.
   */
  public int getWhiteScore();

  /**
   * Determines the current score for black. The score is the number of black disks on the board.
   * @return black's score.
   */
  public int getBlackScore();

  /**
   * Checks if the game is over by seeing if both players passed in a row. This works as a valid
   * method for checking uf the game is over because even if the board is full, they can both pass
   * which will in turn set consecutive moves for both teams to 0, ending the game.
   *
   * @return true if the game is over.
   */
  public boolean isGameOver();

  /**
   * Creates a duplicate hashmap of the board.
   * @return copy of the HashMap of the board
   */
  public Map<CellCoordinate, Cell> copyBoard();

  /**
   * Retrieves the board for this Reversi game.
   * @return the board associated with this game of Reversi.
   */
  public Map<CellCoordinate, Cell> getGameBoard();

  boolean getIsBlacksTurn();
}
