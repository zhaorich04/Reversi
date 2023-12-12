package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.BasicModel;
import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.MaxCaptureStrategy;
import cs3500.reversi.view.ReversiGraphicsView;
import cs3500.reversi.view.ReversiView;

/**
 * Runs a reversi game using the main method.
 */
public class ReversiRunner {
  /**
   * Main method for the runner.
   * @param args list of args.
   */
  public static void main(String[] args) {
    ReversiModel model = new BasicModel(4);

    Player player1 = new HumanPlayer(true);
    Player player2 = new AIPlayer(model, new MaxCaptureStrategy(), false);

    ReversiView viewPlayer1 = new ReversiGraphicsView(model, player1);
    ReversiView viewPlayer2 = new ReversiGraphicsView(model, player2);

    Controller controller1 = new Controller(model, player1, viewPlayer2);
    Controller controller2 = new Controller(model, player2, viewPlayer1);

    player1.addController(controller1);
    player2.addController(controller2);

    model.startGame();
    viewPlayer1.setVisible(true);
    viewPlayer2.setVisible(true);
  }
}


