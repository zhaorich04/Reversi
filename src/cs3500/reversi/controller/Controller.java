package cs3500.reversi.controller;

import cs3500.reversi.Player;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.ModelListeners;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * Controls a game of reversi by making moves and
 * passing turns on the model and updating the view.
 */
public class Controller implements ReversiController, ViewFeatures, ModelListeners {

  ReversiModel model;
  Player player;
  ReversiView view;

  /**
   * Constructs a controller for a game of reversi.
   * @param model the game model.
   * @param player the player associated with this controller.
   * @param view the view that this controller is updating.
   */
  public Controller(ReversiModel model, Player player, ReversiView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.model.addListener(this);
  }

  @Override
  public void notifyChange() {
    view.repaintView();
  }

  @Override
  public void handleMove(Cell selectedCell) {
    if (model.getIsBlacksTurn() == player.getIsBlackPlayer()) {
      try {
        model.makeMove(selectedCell);
      } catch (IllegalArgumentException e) {
        view.displayErrorMessage(e);
      } catch (IllegalStateException e) {
        view.displayErrorStateMessage(e);
      }

    }
  }

  @Override
  public void handlePass() {
    if (model.getIsBlacksTurn() == player.getIsBlackPlayer()) {
      try {
        model.passTurn();
      } catch (IllegalStateException e) {
        view.displayErrorStateMessage(e);
      }

    }
  }
}
