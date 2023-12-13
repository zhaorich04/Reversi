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

  public Map<CellCoordinate, Cell> copyBoard() {
    Map<CellCoordinate, Cell> boardCopy = new HashMap<>();
    for (Cell cell : gameBoard.values()) {
      boardCopy.put(cell.getCoord(), new Cell(cell.getCoord(), cell.getDisk()));
    }
    return boardCopy;
  }

  @Override
  public Map<CellCoordinate, Cell> getGameBoard() {
    return null;
  }

  @Override
  public boolean getIsBlacksTurn() {
    return false;
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
  public boolean hasLegalMove() {
    return false;
  }

  @Override
  public Disk getWinner() {
    return null;
  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public Cell getCell(CellCoordinate coord) {
    return gameBoard[coord.getQ()][coord.getR()];
  }

  @Override
  public boolean isMoveLegal(Cell currentCell) {
//    if (!gameBoard.containsKey(currentCell.getCoord())) {
//      return false;
//    }
//    if (currentCell.isOccupied()) {
//      return false;
//    }
//    return isSquareMoveLegal(currentCell);
    return true;
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

