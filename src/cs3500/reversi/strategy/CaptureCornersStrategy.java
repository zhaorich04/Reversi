package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class to represent the capturing corners strategy.
 */
public class CaptureCornersStrategy implements ReversiStrategy {
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

    List<Cell> listOfCorners = new ArrayList<>();
    listOfCorners.add(model.getCell(topLeftCorner));
    listOfCorners.add(model.getCell(topRightCorner));
    listOfCorners.add(model.getCell(rightCorner));
    listOfCorners.add(model.getCell(bottomRightCorner));
    listOfCorners.add(model.getCell(bottomLeftCorner));
    listOfCorners.add(model.getCell(leftCorner));

    Cell chosenCell = null;
    for (Cell cell : model.getGameBoard().values()) {
      if (model.isMoveLegal(cell) && (listOfCorners.contains(cell)) && (!cell.isOccupied())) {
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
