package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents the strategy where you avoid the neighbors of the corner cell.
 */
public class AvoidNeighborCornerStrategy implements ReversiStrategy {

  /**
   * Returns the valid cell that is not one of the neighbors of the corner cell.
   * @param model ReadOnlyReversiModel
   * @return the cell that is not one of the neighbors of the corner cell
   */
  @Override
  public Cell chooseCell(ReadOnlyReversiModel model) {
    if (!model.hasLegalMove()) {
      throw new IllegalArgumentException();
    }

    CellCoordinate topLeftCorner = new CellCoordinate(0, -model.getBoardSize() + 1);
    CellCoordinate topRightCorner = new CellCoordinate(model.getBoardSize() - 1,
            -model.getBoardSize() + 1);
    CellCoordinate rightCorner = new CellCoordinate(model.getBoardSize() - 1, 0);
    CellCoordinate bottomRightCorner = new CellCoordinate(0, model.getBoardSize() - 1);
    CellCoordinate bottomLeftCorner = new CellCoordinate(-model.getBoardSize() + 1,
            model.getBoardSize() - 1);
    CellCoordinate leftCorner = new CellCoordinate(-model.getBoardSize() + 1, 0);

    List<Cell> neighborsOfCorners = new ArrayList<>();
    neighborsOfCorners.addAll(model.getCell(topLeftCorner).neighborsList(model.getGameBoard()));
    neighborsOfCorners.addAll(model.getCell(topRightCorner).neighborsList(model.getGameBoard()));
    neighborsOfCorners.addAll(model.getCell(rightCorner).neighborsList(model.getGameBoard()));
    neighborsOfCorners.addAll(model.getCell(bottomRightCorner).neighborsList(model.getGameBoard()));
    neighborsOfCorners.addAll(model.getCell(bottomLeftCorner).neighborsList(model.getGameBoard()));
    neighborsOfCorners.addAll(model.getCell(leftCorner).neighborsList(model.getGameBoard()));

    Cell chosenCell = null;
    for (Cell cell : model.getGameBoard().values()) {
      if (model.isMoveLegal(cell) && !(neighborsOfCorners.contains(cell))) {
        chosenCell = cell;
      }
    }
    if (chosenCell == null) {
      for (Cell cell : model.getGameBoard().values()) {
        if (model.isMoveLegal(cell)) {
          chosenCell = cell;
        }
      }
    }
    return chosenCell;
  }
}
