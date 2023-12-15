package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Cell;

/**
 * Class representing a human player for a game of Reversi.
 */
public class HumanPlayer implements Player {

  private Controller controller;

  private final boolean isBlackPlayer;

  /**
   * Constructs a human player.
   * @param isBlackPlayer boolean value determining which team this player is playing for.
   */
  public HumanPlayer(boolean isBlackPlayer) {
    this.isBlackPlayer = isBlackPlayer;
  }

  public void onMoveSelected(Cell selectedCell) {
    controller.handleMove(selectedCell);
  }

  public void passSelected() {
    controller.handlePass();
  }

  public void addController(Controller controller) {
    this.controller = controller;
  }

  @Override
  public boolean getIsBlackPlayer() {
    return isBlackPlayer;
  }

  @Override
  public void hintsEnabled() {
    controller.handleHint();
  }


}
