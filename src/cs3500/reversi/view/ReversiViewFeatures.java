package cs3500.reversi.view;

import cs3500.reversi.Player;
import cs3500.reversi.model.Cell;

/**
 * Class representing the features that the view has.
 */
public class ReversiViewFeatures implements ViewFeatures {

  private Player player;

  public ReversiViewFeatures(Player player) {
    this.player = player;
  }

  @Override
  public void handleMove(Cell selectedCell) {
    player.onMoveSelected(selectedCell);
  }

  @Override
  public void handlePass() {
    player.passSelected();
  }

  @Override
  public void handleHint() {
    player.hintsEnabled();
  }
}
