package cs3500.reversi.view;

import java.awt.Color;

import javax.swing.JFrame;

import cs3500.reversi.Player;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a GUI view for a reversi game.
 */
public class SquareReversiGraphicsView extends JFrame implements ReversiView {
  SquareReversiPanel reversiPanel;
  Player player;

  /**
   * constructs a reversi GUI view.
   * @param model the model.
   */
  public SquareReversiGraphicsView(ReadOnlyReversiModel model, Player player) {
    super();
    this.player = player;
    setTitle("Reversi");
    this.setSize(750,750);
    this.setBackground(Color.DARK_GRAY);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    reversiPanel = new SquareReversiPanel(model);

    ReversiViewListeners viewListeners = new ReversiViewListeners();
    ViewFeatures viewFeatures = new ReversiViewFeatures(player);
    reversiPanel.setViewListener(viewListeners);
    reversiPanel.setFeaturesListener(viewFeatures);
    this.add(reversiPanel);
    setResizable(false);
    setFocusable(true);
  }

  public void repaintView() {
    reversiPanel.repaint();
    repaint();
  }

  @Override
  public void displayErrorMessage(IllegalArgumentException e) {
    reversiPanel.displayErrorMessage(e);
  }

  @Override
  public void displayErrorStateMessage(IllegalStateException e) {
    reversiPanel.displayErrorStateMessage(e);
  }
}
