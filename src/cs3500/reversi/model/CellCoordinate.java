package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class representing a cell's coordinates for placement on a grid.
 */
public class CellCoordinate {
  // "column" coordinate (columns are diagonal for axial coordinates)
  private int q;
  // row coordinate
  private int r;

  /**
   * Constructs a CellCoordinate object with its col and row.
   * @param q the column value.
   * @param r the row value.
   */
  public CellCoordinate(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Retrieves the column value.
   * @return int of the column
   */
  public int getQ() {
    return q;
  }

  /**
   * Retrieves the row value.
   * @return int of the row
   */
  public int getR() {
    return r;
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
    CellCoordinate that = (CellCoordinate) o;
    return q == that.q && r == that.r;
  }

  /**
   * Overrides HashCode for the Hashmap.
   * @return int of the HashCode
   */
  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }

}
