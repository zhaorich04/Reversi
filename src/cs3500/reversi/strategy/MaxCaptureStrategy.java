package cs3500.reversi.strategy;

import cs3500.reversi.model.BasicModel;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.Disk;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents the strategy where you capture as many pieces on this turn as possible.
 */
public class MaxCaptureStrategy implements ReversiStrategy {

  /**
   * Returns the valid cell that captures as many pieces on this turn as possible.
   * @param model ReadOnlyReversiModel
   * @return the cell that captures as many pieces on this turn as possible.
   */
  @Override
  public Cell chooseCell(ReadOnlyReversiModel model) {
    if (!model.hasLegalMove()) {
      throw new IllegalArgumentException();
    }

    int maxCellsFlipped = 0;
    Cell chosenCell = null;

    for (Cell cell : model.copyBoard().values()) {

      int startingScore = model.getWhiteScore();
      BasicModel modelCopy  = new BasicModel(model.copyBoard());

      if (modelCopy.isMoveLegal(cell)) {
        modelCopy.makeMove(cell);
      }
      else {
        continue;
      }

      int newScore = modelCopy.getWhiteScore();
      int difference = Math.abs(startingScore - newScore);

      if (difference == maxCellsFlipped) {
        int cellS = Math.abs(cell.getCoord().getQ() + cell.getCoord().getR());
        int chosenCellS = Math.abs(chosenCell.getCoord().getQ() + chosenCell.getCoord().getR());

        if (cellS > chosenCellS) {
          chosenCell = cell;
        }

        if (cellS == chosenCellS) {
          if (cell.getCoord().getQ() < chosenCell.getCoord().getQ()) {
            chosenCell = cell;
          }
        }
      }
      if (difference > maxCellsFlipped) {
        maxCellsFlipped = difference;
        chosenCell = cell;
      }
    }
    assert chosenCell != null : "chosenCell should never be null, as the model is checked for"
            + " having a legal move before anything else";
    chosenCell.setDisk(Disk.EMPTY);
    return chosenCell;
  }
}
