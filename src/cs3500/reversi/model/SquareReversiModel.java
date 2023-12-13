package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SquareReversiModel implements ReversiModel, ModelFeatures {

  private List<ModelListeners> modelListeners;
  private boolean gameStarted;
  private boolean isBlacksTurn;
  private int numConsecutiveMovesWhite;
  private int numConsecutiveMovesBlack;
  private final int boardSize;
  private final Cell[][] gameBoard;
  private final int[][] initVectors;

  public SquareReversiModel(int boardSize) {
    if (boardSize < 4 || boardSize % 2 != 0) {
      throw new IllegalArgumentException("Board size must be an even number greater than or equal to 3");
    }
    this.boardSize = boardSize;
    this.gameBoard = new Cell[boardSize][boardSize];
    this.isBlacksTurn = true;
    this.numConsecutiveMovesBlack = 1;
    this.numConsecutiveMovesWhite = 1;
    this.gameStarted = false;
    // top left, top, top right, right, bottom right, bottom, bottom left, left
    this.initVectors = new int[][]{{boardSize / 2 - 1, boardSize / 2 - 1},
            {boardSize / 2, boardSize / 2 - 1}, {boardSize / 2, boardSize / 2 - 1},
            {boardSize / 2, boardSize / 2}};
    this.modelListeners = new ArrayList<>();
  }

  public void startGame() {
    for (int col = 0; col < boardSize; col++) {
      for (int row = 0; row < boardSize; row++) {
        Cell cell = new Cell(new CellCoordinate(col, row));
        gameBoard[col][row] = cell;
      }
    }

    for (int[] v : initVectors) {
      if (v[0] == v[1]) {
        getCell(new CellCoordinate(v[0], v[1])).setDisk(Disk.BLACK);
      } else {
        getCell(new CellCoordinate(v[0], v[1])).setDisk(Disk.WHITE);
      }
    }

    gameStarted = true;
  }

  @Override
  public void makeMove(Cell currentCell) {
    return;
  }

  @Override
  public void passTurn() {
    return;
  }

  @Override
  public void addListener(ModelListeners listeners) {

  }

  @Override
  public Map<CellCoordinate, Cell> getGameBoard() {
    return null;
  }

  @Override
  public boolean getIsBlacksTurn() {
    return isBlacksTurn;
  }

  @Override
  public int countFlippedDiscs(Cell cell) {
    return 0;
  }


//  private boolean isSquareMoveLegal(Cell currentCell) {
//    return topLeftLegalMove(currentCell)
//            || topRightLegalMove(currentCell)
//            || rightLegalMove(currentCell)
//            || bottomRightLegalMove(currentCell)
//            || bottomLeftLegalMove(currentCell)
//            || leftLegalMove(currentCell)
//            || topLegalMove(currentCell)
//            || bottomLegalMove(currentCell);
//  }
//
//  private boolean topLegalMove(Cell currentCell) {
//    Disk diskCurrent = isBlacksTurn ? Disk.BLACK : Disk.WHITE;
//    Disk diskOpponent = isBlacksTurn ? Disk.WHITE : Disk.BLACK;
//
//    try {
//      Cell topNeighbor = currentCell.getTopNeighbor(gameBoard);
//      if (topNeighbor.getDisk() == diskOpponent
//              && topNeighbor.getTopNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
//        if (topNeighbor.getTopNeighbor(gameBoard).getDisk() == diskCurrent) {
//          return true;
//        }
//        return topLegalMove(topNeighbor);
//      }
//      return false;
//    } catch (IndexOutOfBoundsException e) {
//      return false;
//    }
//  }
//
//  private boolean bottomLegalMove(Cell currentCell) {
//    Disk diskCurrent = isBlacksTurn ? Disk.BLACK : Disk.WHITE;
//    Disk diskOpponent = isBlacksTurn ? Disk.WHITE : Disk.BLACK;
//
//    try {
//      Cell bottomNeighbor = currentCell.getBottomNeighbor(gameBoard);
//      if (bottomNeighbor.getDisk() == diskOpponent
//              && bottomNeighbor.getBottomNeighbor(gameBoard).getDisk() != Disk.EMPTY) {
//        if (bottomNeighbor.getBottomNeighbor(gameBoard).getDisk() == diskCurrent) {
//          return true;
//        }
//        return bottomLegalMove(bottomNeighbor);
//      }
//      return false;
//    } catch (IndexOutOfBoundsException e) {
//      return false;
//    }
//  }

  @Override
  public Disk getWinner() {
    return null;
  }

  @Override
  public int getBoardSize() {
    return boardSize;
  }

  @Override
  public Cell getCell(CellCoordinate coord) {
    return gameBoard[coord.getQ()][coord.getR()];
  }

  /**
   * Determines if placing a given cell is a valid move.
   * @param currentCell given cell
   * @return true if the move is legal
   */
  public boolean isMoveLegal(Cell currentCell) {
    if (gameBoard[currentCell.getCoord().getQ()][currentCell.getCoord().getR()] == null) {
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
    for (Cell[] col : gameBoard) {
      for (Cell row : col) {
        if (isMoveLegal(row)) {
          return true;
        }
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
            || leftLegalMove(currentCell)
            || topMiddleLegalMove(currentCell)
            || bottomMiddleLegalMove(currentCell);
  }

  private boolean bottomMiddleLegalMove(Cell currentCell) {
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

  private boolean topMiddleLegalMove(Cell currentCell) {
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

  @Override
  public int getWhiteScore() {
    return 0;
  }

  @Override
  public int getBlackScore() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public void notifyAction() {

  }
}

