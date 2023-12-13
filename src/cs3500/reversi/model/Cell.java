package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Cell on the board.
 */
public class Cell {
  // cell coordinate of the cell
  private final CellCoordinate coord;
  // disk of the cell
  private Disk disk;

  /**
   * Represents the constructor for a Cell.
   * @param coord given CellCoordinate
   */
  public Cell(CellCoordinate coord) {
    this.coord = coord;
    this.disk = Disk.EMPTY;
  }

  public Cell(CellCoordinate coord, Disk disk) {
    this.coord = coord;
    this.disk = disk;
  }

  /**
   * Retrieves the CellCoordinate of the cell.
   * @return CellCoordinate of the cell
   */
  public CellCoordinate getCoord() {
    return coord;
  }

  /**
   * Retrieves the Disk of the cell.
   * @return Disk of the cell
   */
  public Disk getDisk() {
    return disk;
  }

  /**
   * Sets the Cell a new Disk.
   * @param d given Disk
   */
  public void setDisk(Disk d) {
    disk = d;
  }

  /**
   * Determines if the cell is occupied.
   * @return true if the disk is occupied
   */
  public boolean isOccupied() {
    return this.disk != Disk.EMPTY;
  }

  /**
   * Converts the disk into a String.
   * @return a String for the given disk
   */
  @Override
  public String toString() {
    if (this.disk == Disk.WHITE) {
      return "X ";
    }
    if (this.disk == Disk.BLACK) {
      return "O ";
    }
    return "_ ";
  }

  /**
   * Retrieves the top left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the top left most neighbor
   */
  Cell getTopLeftNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate topLeft = new CellCoordinate(this.coord.getQ(), this.coord.getR() - 1);
    if (!board.containsKey(topLeft)) {
      throw new IndexOutOfBoundsException("This cell does not have a top left neighbor");
    }
    return board.get(topLeft);
  }

  /**
   * Retrieves the top right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the top right most neighbor
   */
  Cell getTopRightNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate topRight = new CellCoordinate(this.coord.getQ() + 1, this.coord.getR() - 1);
    if (!board.containsKey(topRight)) {
      throw new IndexOutOfBoundsException("This cell does not have a top right neighbor");
    }
    return board.get(topRight);
  }

  /**
   * Retrieves the right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the right most neighbor
   */
  Cell getRightNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate right = new CellCoordinate(this.coord.getQ() + 1, this.coord.getR());
    if (!board.containsKey(right)) {
      throw new IndexOutOfBoundsException("This cell does not have a right neighbor");
    }
    return board.get(right);
  }

  /**
   * Retrieves the left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the left most neighbor
   */
  Cell getLeftNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate left = new CellCoordinate(this.coord.getQ() - 1, this.coord.getR());
    if (!board.containsKey(left)) {
      throw new IndexOutOfBoundsException("This cell does not have a left neighbor");
    }
    return board.get(left);
  }


  /**
   * Retrieves the bottom right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom right most neighbor
   */
  Cell getBottomRightNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate bottomRight = new CellCoordinate(this.coord.getQ(), this.coord.getR() + 1);
    if (!board.containsKey(bottomRight)) {
      throw new IndexOutOfBoundsException("This cell does not have a bottom right neighbor");
    }
    return board.get(bottomRight);
  }

  /**
   * Retrieves the bottom left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom left most neighbor
   */
  Cell getBottomLeftNeighbor(Map<CellCoordinate, Cell> board) {
    CellCoordinate bottomLeft = new CellCoordinate(this.coord.getQ() - 1, this.coord.getR() + 1);
    if (!board.containsKey(bottomLeft)) {
      throw new IndexOutOfBoundsException("This cell does not have a bottom left neighbor");
    }
    return board.get(bottomLeft);
  }

  // FOR SQUARE REVERSI:

  /**
   * Retrieves the top left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the top left most neighbor
   */
  Cell getTopLeftNeighbor(Cell[][] board) {
    return board[this.coord.getQ() - 1][this.coord.getR() - 1];
  }

  /**
   * Retrieves the bottom most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom left most neighbor
   */
  Cell getTopNeighbor(Cell[][] board) {
    return board[this.coord.getQ()][this.coord.getR() - 1];
  }
  /**
   * Retrieves the top right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the top right most neighbor
   */
  Cell getTopRightNeighbor(Cell[][] board) {
    return board[this.coord.getQ() + 1][this.coord.getR() - 1];
  }

  /**
   * Retrieves the right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the right most neighbor
   */
  Cell getRightNeighbor(Cell[][] board) {
    return board[this.coord.getQ() + 1][this.coord.getR()];
  }

  /**
   * Retrieves the bottom right most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom right most neighbor
   */
  Cell getBottomRightNeighbor(Cell[][] board) {
    return board[this.coord.getQ() + 1][this.coord.getR() + 1];
  }

  /**
   * Retrieves the bottom most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom left most neighbor
   */
  Cell getBottomNeighbor(Cell[][] board) {
    return board[this.coord.getQ()][this.coord.getR() + 1];
  }

  /**
   * Retrieves the bottom left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the bottom left most neighbor
   */
  Cell getBottomLeftNeighbor(Cell[][] board) {
    return board[this.coord.getQ() - 1][this.coord.getR() + 1];
  }

  /**
   * Retrieves the left most neighbor.
   * @param board given gameBoard
   * @return the Cell that is the left most neighbor
   */
  Cell getLeftNeighbor(Cell[][] board) {
    return board[this.coord.getQ() - 1][this.coord.getR()];
  }



  /**
   * Returns this cell's neighbors.
   * @param board the game board.
   * @return the neighbors of this cell.
   */
  public List<Cell> neighborsList(Map<CellCoordinate, Cell> board) {
    ArrayList<Cell> neighbors = new ArrayList<>();

    try {
      neighbors.add(getLeftNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    try {
      neighbors.add(getTopLeftNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    try {
      neighbors.add(getTopRightNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    try {
      neighbors.add(getRightNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    try {
      neighbors.add(getBottomRightNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    try {
      neighbors.add(getBottomLeftNeighbor(board));
    } catch (IndexOutOfBoundsException ignored) {
    }

    return neighbors;
  }

  /**
   * Overrides Equals for the HashMap.
   * @param o given CellCoordinate
   * @return true if the CellCoordinates are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell that = (Cell) o;
    return coord.equals(that.coord) && disk == that.disk;
  }

  /**
   * Overrides HashCode for the Hashmap.
   * @return int of the HashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(coord, disk);
  }


}
