package cs3500.reversi.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a BasicModel of the game Reversi.
 * Reversi is a two-player game played on a regular grid of cells.
 * Each player has a color—black or white—and the game pieces are discs
 * colored black on one side and white on the other.
 */
public class BasicModel implements ReversiModel, ModelFeatures {

  private List<ModelListeners> modelListeners;
  // boolean to represent whether the game has been started
  private boolean gameStarted;
  // boolean to represent whether it is Black's turn
  private boolean isBlacksTurn;
  // int to represent the number of consecutive moves White makes.
  private int numConsecutiveMovesWhite;
  // int to represent the number of consecutive moves Black makes.
  private int numConsecutiveMovesBlack;
  // int to represent the board size of the BasicModel of Reversi.
  private final int boardSize;
  // Hashmap to represent the gameBoard.
  // Maps the CellCoordinate to the Cell.
  private final Map<CellCoordinate, Cell> gameBoard;
  // 2D array to represent the initial vectors surrounding the center of the board
  private final int[][] initVectors;

  /**
   * Constructor for a Basic Model of Reversi.
   * @param boardSize int for given board size
   */
  public BasicModel(int boardSize) {
    // CLASS INVARIANT: if boardSize is less than 3 than the game can not be played
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be 3 or greater");
    }
    this.boardSize = boardSize;
    this.gameBoard = new HashMap<>();
    this.isBlacksTurn = true;
    // initialized as 1 so that exceptions are not caught at the start of the game
    this.numConsecutiveMovesBlack = 1;
    // initialized as 1 so that exceptions are not caught at the start of the game
    this.numConsecutiveMovesWhite = 1;
    // 2D array representing the coordinates surrounding the center of the board
    this.initVectors = new int[][]{{0, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 0}, {1, -1}};
    this.gameStarted = false;
    this.modelListeners = new ArrayList<>();
  }

  /**
   * Constructs a basic model with a gameBoard to be used for making a model copy.
   * @param gameBoard the given gameBoard.
   */
  public BasicModel(Map<CellCoordinate, Cell> gameBoard) {
    this.gameBoard = gameBoard;
    this.boardSize = this.getBoardSize();
    // 2D array representing the coordinates surrounding the center of the board
    this.initVectors = new int[][]{{0, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 0}, {1, -1}};
    // initialized as 1 so that exceptions are not caught at the start of the game
    this.numConsecutiveMovesBlack = 1;
    // initialized as 1 so that exceptions are not caught at the start of the game
    this.numConsecutiveMovesWhite = 1;
  }

  /**
   * Starts the game with the initial board state using an axial coordinate system.
   */
  public void startGame() {
    for (int q = -boardSize + 1; q < boardSize; q++) {
      for (int r = Math.max(-boardSize + 1, -q - boardSize + 1);
           r < Math.min(boardSize, -q + boardSize); r++) {
        CellCoordinate coordinate = new CellCoordinate(q, r);
        Cell cell = new Cell(coordinate);
        addCell(cell);
      }
    }

    for (int cell = 0; cell < initVectors.length; cell++) {
      for (int[] v : initVectors) {
        Cell currentCell = gameBoard.get(new CellCoordinate(v[0], v[1]));
        if (cell % 2 == 1) {
          currentCell.setDisk(Disk.BLACK);
        } else {
          currentCell.setDisk(Disk.WHITE);
        }
        cell++;
      }
    }
    gameStarted = true;
  }

  /**
   * Creates a duplicate hashmap of the board.
   * @return copy of the HashMap of the board
   */
  public Map<CellCoordinate, Cell> copyBoard() {
    Map<CellCoordinate, Cell> boardCopy = new HashMap<>();
    for (Cell cell : gameBoard.values()) {
      boardCopy.put(cell.getCoord(), new Cell(cell.getCoord(), cell.getDisk()));
    }
    return boardCopy;
  }

  /**
   * Retrieves the board for this Reversi game.
   * @return the board associated with this game of Reversi.
   */
  public Map<CellCoordinate, Cell> getGameBoard() {
    return gameBoard;
  }

  /**
   * Adds a cell to the gameBoard.
   * @param cell given cell wanting to add
   */
  private void addCell(Cell cell) {
    gameBoard.put(cell.getCoord(), cell);
  }

  /**
   * Retrieves a cell given a CellCoordinate.
   * @param coordinate given CellCoordinate
   * @return the Cell at the given CellCoordinate
   */
  public Cell getCell(CellCoordinate coordinate) {
    return gameBoard.get(coordinate);
  }

  /**
   * Retrieves the board size of the game.
   * @return the board size
   */
  public int getBoardSize() {
    return boardSize;
  }

  /**
   * Determines if placing a given cell is a valid move.
   * @param currentCell given cell
   * @return true if the move is legal
   */
  public boolean isMoveLegal(Cell currentCell) {
    if (!gameBoard.containsKey(currentCell.getCoord())) {
      return false;
    }
    if (currentCell.isOccupied()) {
      return false;
    }
    return isMoveLegalHelper(currentCell);
  }

  /**
   * Returns whether the current player has a legal move.
   * @return true if the current player has a legal move.
   */
  public boolean hasLegalMove() {
    for (Cell cell : gameBoard.values()) {
      if (isMoveLegal(cell)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if placing a given cell is valid by checking all of the cell's neighbors.
   * @param currentCell given cell
   * @return true if at least of the cell's neighbors has a valid move
   */
  private boolean isMoveLegalHelper(Cell currentCell) {
    return bottomLeftLegalMove(currentCell)
            || bottomRightLegalMove(currentCell)
            || topLeftLegalMove(currentCell)
            || topRightLegalMove(currentCell)
            || rightLegalMove(currentCell)
            || leftLegalMove(currentCell);
  }

  /**
   * Determines if the move is valid for it's bottom left most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean bottomLeftLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell bottomLeftNeighbor = currentCell.getBottomLeftNeighbor(gameBoard);
      if (bottomLeftNeighbor.getDisk() == diskOpponent
              && bottomLeftNeighbor.getBottomLeftNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (bottomLeftNeighbor.getBottomLeftNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return bottomLeftLegalMove(bottomLeftNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the move is valid for it's bottom right most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean bottomRightLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell bottomRightNeighbor = currentCell.getBottomRightNeighbor(gameBoard);
      if (bottomRightNeighbor.getDisk() == diskOpponent
              && bottomRightNeighbor.getBottomRightNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (bottomRightNeighbor.getBottomRightNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return bottomRightLegalMove(bottomRightNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the move is valid for it's top right most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean topRightLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell topRightNeighbor = currentCell.getTopRightNeighbor(gameBoard);
      if (topRightNeighbor.getDisk() == diskOpponent
              && topRightNeighbor.getTopRightNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (topRightNeighbor.getTopRightNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return topRightLegalMove(topRightNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the move is valid for it's top left most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean topLeftLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell topLeftNeighbor = currentCell.getTopLeftNeighbor(gameBoard);
      if (topLeftNeighbor.getDisk() == diskOpponent
              && topLeftNeighbor.getTopLeftNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (topLeftNeighbor.getTopLeftNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return topLeftLegalMove(topLeftNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the move is valid for it's right most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean rightLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell rightNeighbor = currentCell.getRightNeighbor(gameBoard);
      if (rightNeighbor.getDisk() == diskOpponent
              && rightNeighbor.getRightNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (rightNeighbor.getRightNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return rightLegalMove(rightNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the move is valid for it's left most neighbor.
   * @param currentCell given cell
   * @return true if the move is valid
   */
  private boolean leftLegalMove(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }
    try {
      Cell leftNeighbor = currentCell.getLeftNeighbor(gameBoard);
      if (leftNeighbor.getDisk() == diskOpponent
              && leftNeighbor.getLeftNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
        if (leftNeighbor.getLeftNeighbor(gameBoard).getDisk() == diskCurrent) {
          return true;
        }
        return leftLegalMove(leftNeighbor);
      }
      return false;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Makes a "move" for either Black or White's turn.
   * @param currentCell given cell
   * @throws IllegalStateException if the game is not over.
   */
  @Override
  public void makeMove(Cell currentCell) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
    if (!isMoveLegal(currentCell)) {
      throw new IllegalArgumentException("Move is illegal");
    }
    if (isBlacksTurn) {
      currentCell.setDisk(Disk.BLACK);
      gameBoard.put(currentCell.getCoord(), currentCell);
      flipCells(currentCell);
      isBlacksTurn = false;
    } else {
      currentCell.setDisk(Disk.WHITE);
      gameBoard.put(currentCell.getCoord(), currentCell);
      flipCells(currentCell);
      isBlacksTurn = true;
    }
  }

  public boolean getIsBlacksTurn() {
    return isBlacksTurn;
  }

  @Override
  public int countFlippedDiscs(Cell cell) {

    if (isMoveLegal(cell)) {
      int flippedDiscs = 0;
      flippedDiscs += countDirection(cell, 0, -1);
      flippedDiscs += countDirection(cell, 1, -1);
      flippedDiscs += countDirection(cell, 1, 0);
      flippedDiscs += countDirection(cell, 0, 1);
      flippedDiscs += countDirection(cell, -1, 1);
      flippedDiscs += countDirection(cell, -1, 0);

      return flippedDiscs;
    }

    return 0;
  }

  private int countDirection(Cell currentCell, int dirQ, int dirR) {
    int count = 0;
    CellCoordinate coordinate = currentCell.getCoord();

    try {
      Cell neighbor = getCell(new CellCoordinate(coordinate.getQ() + dirQ,
              coordinate.getR() + dirR));
      Disk currentDisk = isBlacksTurn ? Disk.BLACK : Disk.WHITE;
      Disk opponentDisk = isBlacksTurn ? Disk.WHITE : Disk.BLACK;
      while (neighbor != null && neighbor.getDisk() == opponentDisk) {
        count++;
        coordinate = neighbor.getCoord();
        neighbor = getCell(new CellCoordinate(coordinate.getQ() + dirQ, coordinate.getR() + dirR));
      }

      if (neighbor != null && neighbor.getDisk() == currentDisk) {
        return count;
      }
    } catch (IndexOutOfBoundsException ignored) {

    }

    return 0;
  }


  /**
   * Flips its neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipCells(Cell currentCell) {
    if (topLeftLegalMove(currentCell)) {
      flipTopLeft(currentCell);
    }
    if (topRightLegalMove(currentCell)) {
      flipTopRight(currentCell);
    }
    if (rightLegalMove(currentCell)) {
      flipRight(currentCell);
    }
    if (bottomRightLegalMove(currentCell)) {
      flipBottomRight(currentCell);
    }
    if (bottomLeftLegalMove(currentCell)) {
      flipBottomLeft(currentCell);
    }
    if (leftLegalMove(currentCell)) {
      flipLeft(currentCell);
    }
  }

  /**
   * Flips the top left neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipTopLeft(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell topLeftNeighbor = currentCell.getTopLeftNeighbor(gameBoard);
    if (topLeftNeighbor.getDisk() == diskOpponent) {
      topLeftNeighbor.setDisk(diskCurrent);
      flipTopLeft(topLeftNeighbor);
    }
  }

  /**
   * Flips the top right neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipTopRight(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell topRightNeighbor = currentCell.getTopRightNeighbor(gameBoard);
    if (topRightNeighbor.getDisk() == diskOpponent) {
      topRightNeighbor.setDisk(diskCurrent);
      flipTopRight(topRightNeighbor);
    }
  }

  /**
   * Flips the right neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipRight(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell rightNeighbor = currentCell.getRightNeighbor(gameBoard);
    if (rightNeighbor.getDisk() == diskOpponent) {
      rightNeighbor.setDisk(diskCurrent);
      flipRight(rightNeighbor);
    }
  }

  /**
   * Flips the left neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipLeft(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell leftNeighbor = currentCell.getLeftNeighbor(gameBoard);
    if (leftNeighbor.getDisk() == diskOpponent) {
      leftNeighbor.setDisk(diskCurrent);
      flipLeft(leftNeighbor);
    }
  }

  /**
   * Flips the bottom right neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipBottomRight(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell bottomRightNeighbor = currentCell.getBottomRightNeighbor(gameBoard);
    if (bottomRightNeighbor.getDisk() == diskOpponent) {
      bottomRightNeighbor.setDisk(diskCurrent);
      flipBottomRight(bottomRightNeighbor);
    }
  }

  /**
   * Flips the bottom left neighboring cells given a cell.
   * @param currentCell given cell
   */
  private void flipBottomLeft(Cell currentCell) {
    Disk diskCurrent;
    Disk diskOpponent;

    if (isBlacksTurn) {
      diskCurrent = Disk.BLACK;
      diskOpponent = Disk.WHITE;
    } else {
      diskCurrent = Disk.WHITE;
      diskOpponent = Disk.BLACK;
    }

    Cell bottomLeftNeighbor = currentCell.getBottomLeftNeighbor(gameBoard);
    if (bottomLeftNeighbor.getDisk() == diskOpponent) {
      bottomLeftNeighbor.setDisk(diskCurrent);
      flipBottomLeft(bottomLeftNeighbor);
    }
  }

  /**
   * Passes the turn.
   * @throws IllegalStateException if the game is not over.
   */
  @Override
  public void passTurn() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over!");
    }
    if (isBlacksTurn) {
      numConsecutiveMovesBlack = 0;
      isBlacksTurn = false;
    } else {
      numConsecutiveMovesWhite = 0;
      isBlacksTurn = true;
    }
  }

  /**
   * Checks if the game is over by seeing if both players passed in a row. This works as a valid
   * method for checking uf the game is over because even if the board is full, they can both pass
   * which will in turn set consecutive moves for both teams to 0, ending the game.
   *
   * @return true if the game is over.
   */
  public boolean isGameOver() {
    return (numConsecutiveMovesBlack == 0 && numConsecutiveMovesWhite == 0
            || (getBlackScore() > getWhiteScore() && !hasLegalMove())
            || (getBlackScore() < getWhiteScore() && !hasLegalMove()))
            || (getBlackScore() == getWhiteScore() && !hasLegalMove());
  }

  /**
   * Determines the current score for white. The score is the number of white disks on the board.
   * @return white's score.
   */
  public int getWhiteScore() {
    int score = 0;

    for (Cell cell : gameBoard.values()) {
      if (cell.getDisk() == Disk.WHITE) {
        score++;
      }
    }
    return score;
  }

  /**
   * Determines the current score for black. The score is the number of black disks on the board.
   * @return black's score.
   */
  public int getBlackScore() {
    int score = 0;

    for (Cell cell : gameBoard.values()) {
      if (cell.getDisk() == Disk.BLACK) {
        score++;
      }
    }
    return score;
  }

  /**
   * Retrieves the disk of the winning player.
   * @return Disk of the winning player
   * @throws IllegalStateException if the game is not over.
   */
  @Override
  public Disk getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over!");
    }

    if (getBlackScore() > getWhiteScore()) {
      return Disk.BLACK;
    }
    if (getBlackScore() < getWhiteScore()) {
      return Disk.WHITE;
    }
    return Disk.EMPTY;
  }


  @Override
  public void notifyAction() {
    for (ModelListeners listeners : modelListeners) {
      listeners.notifyChange();
    }
  }

  @Override
  public void addListener(ModelListeners listeners) {
    this.modelListeners.add(listeners);
  }
}
