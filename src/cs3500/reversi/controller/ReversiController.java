package cs3500.reversi.controller;

import cs3500.reversi.model.Cell;

/**
 * Interface representing the controller behaviors that update the model.
 */
public interface ReversiController {

  void handleMove(Cell selectedCell);

  void handlePass();

}
