package cs3500.reversi.view;

/**
 * Represents the interface for a Reversi.
 */
public interface ReversiView {

  /**
   * Sets the JFrame visible.
   * @param b boolean to set the JFrame visible.
   */
  void setVisible(boolean b);

  void repaintView();

  void displayErrorMessage(IllegalArgumentException e);

  void displayErrorStateMessage(IllegalStateException e);

}
