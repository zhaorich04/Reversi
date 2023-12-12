package cs3500.reversi.view;

import cs3500.reversi.model.Cell;

/**
 * The features that the view has for handling moves and passes.
 */
public interface ViewFeatures {

  public void handleMove(Cell selectedCell);

  public void handlePass();

  void handleHint();
}
