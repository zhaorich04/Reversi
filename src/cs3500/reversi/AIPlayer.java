package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * Class representing an AI / strategy based player for a game of Reversi.
 */
public class AIPlayer implements Player {

  // can manipulate the view directly
  private ReversiModel model;
  private final ReversiStrategy strategy;

  private boolean isBlackPlayer;

  private Controller controller;

  /**
   * Constructor for an AI player.
   * @param model the game model.
   * @param strategy the strategy to use.
   * @param isBlackPlayer boolean value determining which team this player is playing for.
   */
  public AIPlayer(ReversiModel model, ReversiStrategy strategy, boolean isBlackPlayer) {
    this.model = model;
    this.strategy = strategy;
    this.isBlackPlayer = isBlackPlayer;
  }

  @Override
  public void onMoveSelected(Cell selectedCell) {
    Cell cell = strategy.chooseCell(model);
    controller.handleMove(cell);
  }

  @Override
  public void passSelected() {
    controller.handlePass();
  }

  @Override
  public void addController(Controller controller) {
    this.controller = controller;
  }

  public boolean getIsBlackPlayer() {
    return isBlackPlayer;
  }
}
