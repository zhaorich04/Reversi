package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.SquareReversiModel;
import cs3500.reversi.strategy.AvoidNeighborCornerStrategy;
import cs3500.reversi.strategy.CaptureCornersStrategy;
import cs3500.reversi.strategy.MaxCaptureStrategy;
import cs3500.reversi.view.ReversiHint;
import cs3500.reversi.view.ReversiView;
import cs3500.reversi.view.SquareReversiGraphicsView;

/**
 * Runs a reversi game using the main method.
 */
public class ReversiRunner {
  /**
   * Main method for the runner.
   * @param args list of args.
   */
  public static void main(String[] args) {
    SquareReversiModel model = new SquareReversiModel(8);

    Player player1 = null;
    Player player2 = null;
    for (String s : args) {
      switch (s) {
        case "human":
          if (player1 == null) {
            player1 = new HumanPlayer(true);
          }
          else {
            player2 = new HumanPlayer(false);
          }
          break;
        case "strategy1": {
          if (player1 == null) {
            player1 = new AIPlayer(model, new MaxCaptureStrategy(), true);
          }
          else {
            player2 = new AIPlayer(model, new MaxCaptureStrategy(), false);
          }
          break;
        }
        case "strategy2": {
          if (player1 == null) {
            player1 = new AIPlayer(model, new CaptureCornersStrategy(), true);
          }
          else {
            player2 = new AIPlayer(model, new CaptureCornersStrategy(), false);
          }
          break;
        }
        case "strategy3": {
          if (player1 == null) {
            player1 = new AIPlayer(model, new AvoidNeighborCornerStrategy(), true);
          }
          else {
            player2 = new AIPlayer(model, new AvoidNeighborCornerStrategy(), false);
          }
          break;
        }
        default :
          player1 = new HumanPlayer(true);
          player2 = new HumanPlayer(false);
      }
    }


    ReversiView viewPlayer1 = new SquareReversiGraphicsView(model, player1);
    ReversiView viewPlayer2 = new SquareReversiGraphicsView(model, player2);
    ReversiHint reversiHint = new ReversiHint(model);

    Controller controller1 = new Controller(model, player1, reversiHint);
    Controller controller2 = new Controller(model, player2, reversiHint);

    assert player1 != null;
    player1.addController(controller1);
    assert player2 != null;
    player2.addController(controller2);

    model.startGame();
    viewPlayer1.setVisible(true);
    viewPlayer2.setVisible(true);
  }
}


