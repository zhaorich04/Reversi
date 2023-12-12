package cs3500.reversi.strategy;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Interface to represent an AI strategy for a Reversi game.
 */
public interface ReversiStrategy {
  Cell chooseCell(ReadOnlyReversiModel model);
}
