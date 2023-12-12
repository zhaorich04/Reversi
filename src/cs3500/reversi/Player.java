package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Cell;

/**
 * Represents a Player of the game (Person or AI).
 */
public interface Player {

  void onMoveSelected(Cell selectedCell);

  void passSelected();

  void addController(Controller controller);

  boolean getIsBlackPlayer();

  void hintsEnabled();
}
