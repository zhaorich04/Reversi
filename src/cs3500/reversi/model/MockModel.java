package cs3500.reversi.model;


import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a BasicModel of the game Reversi.
 * Reversi is a two-player game played on a regular grid of cells.
 * Each player has a color—black or white—and the game pieces are discs
 * colored black on one side and white on the other.
 */
public class MockModel implements ReversiModel {
  final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Returns whether the current player has a legal move.
   *
   * @return true if the current player has a legal move.
   */
  @Override
  public boolean hasLegalMove() {
    log.append(String.format("hasLegalMove was called"));
    return false;
  }

  /**
   * Retrieves the disk of the winning player.
   *
   * @return Disk of the winning player
   */
  @Override
  public Disk getWinner() {
    log.append(String.format("getWinner was called"));
    return null;
  }

  @Override
  public int getBoardSize() {
    log.append(String.format("getBoardSize was called"));
    return 0;
  }

  /**
   * Retrieves a cell given a CellCoordinate.
   *
   * @param coordinate given CellCoordinate
   * @return the Cell at the given CellCoordinate
   */
  @Override
  public Cell getCell(CellCoordinate coordinate) {
    log.append(String.format("coordinate.getQ() = %d, coordinate.getR() = %d\n",
            coordinate.getQ(), coordinate.getR()));
    return null;
  }

  /**
   * Determines if placing a given cell is a valid move.
   *
   * @param currentCell given cell
   * @return true if the move is legal
   */
  @Override
  public boolean isMoveLegal(Cell currentCell) {
    return false;
  }

  /**
   * Determines the current score for white. The score is the number of white disks on the board.
   *
   * @return white's score.
   */
  @Override
  public int getWhiteScore() {
    log.append(String.format("getWhiteScore was called"));
    return 0;
  }

  /**
   * Determines the current score for black. The score is the number of black disks on the board.
   *
   * @return black's score.
   */
  @Override
  public int getBlackScore() {
    log.append(String.format("getBlackScore was called"));
    return 0;
  }

  /**
   * Checks if the game is over by seeing if both players passed in a row. This works as a valid
   * method for checking uf the game is over because even if the board is full, they can both pass
   * which will in turn set consecutive moves for both teams to 0, ending the game.
   *
   * @return true if the game is over.
   */
  @Override
  public boolean isGameOver() {
    log.append(String.format("isGameOver was called"));
    return false;
  }

  /**
   * Creates a duplicate hashmap of the board.
   *
   * @return copy of the HashMap of the board
   */
  @Override
  public HashMap<CellCoordinate, Cell> copyBoard() {
    log.append(String.format("copyBoard was called"));
    return null;
  }

  /**
   * Retrieves the board for this Reversi game.
   *
   * @return the board associated with this game of Reversi.
   */
  @Override
  public HashMap<CellCoordinate, Cell> getGameBoard() {
    log.append(String.format("getGameBoard was called"));
    return null;
  }

  @Override
  public boolean getIsBlacksTurn() {
    return false;
  }

  /**
   * Starts the game with the initial board state using an axial coordinate system.
   */
  @Override
  public void startGame() {
    log.append(String.format("startGame was called"));
  }

  /**
   * Makes a "move" for either Black or White's turn.
   *
   * @param currentCell given cell
   */
  @Override
  public void makeMove(Cell currentCell) {
    log.append(String.format("A move was made at"));
  }

  /**
   * Passes the turn.
   */
  @Override
  public void passTurn() {
    log.append(String.format("passTurn was called"));
  }

  @Override
  public void addListener(ModelListeners listeners) {
    log.append(String.format("addListener was called"));

  }
}
