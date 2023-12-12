package cs3500.reversi.controller;

import cs3500.reversi.Player;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.ModelListeners;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ReversiView;

/**
 * A mock implementation of the ReversiController for testing purposes.
 */
public class MockController implements ReversiController, ModelListeners {

  private final ReversiModel model;
  private final Player player;
  private final ReversiView view;
  private final StringBuilder log;

  /**
   * Constructs a MockController.
   *
   * @param model  the ReversiModel to be used
   * @param player the Player to be used
   * @param view   the ReversiView to be used
   * @param log    the StringBuilder for logging method calls
   */
  public MockController(ReversiModel model, Player player, ReversiView view, StringBuilder log) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.log = log;
    this.model.addListener(this);
  }

  @Override
  public void notifyChange() {
    log.append("notifyChange was called\n");
  }

  @Override
  public void handleMove(Cell selectedCell) {
    if (model.getIsBlacksTurn() == player.getIsBlackPlayer()) {
      try {
        model.makeMove(selectedCell);
        log.append("handleMove was called\n");
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e);
        log.append("handleMove threw IllegalArgumentException\n");
      } catch (IllegalStateException e) {
        view.displayErrorStateMessage(e);
        log.append("handleMove threw IllegalStateException\n");
      }
    }
  }

  @Override
  public void handlePass() {
    if (model.getIsBlacksTurn() == player.getIsBlackPlayer()) {
      try {
        model.passTurn();
        log.append("handlePass was called\n");
      } catch (IllegalStateException e) {
        view.displayErrorStateMessage(e);
        log.append("handlePass threw IllegalStateException\n");
      }
    }
  }
}