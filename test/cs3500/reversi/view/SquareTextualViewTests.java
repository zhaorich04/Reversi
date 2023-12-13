package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicModel;
import cs3500.reversi.model.SquareReversiModel;

public class SquareTextualViewTests {
  @Test
  public void testInitialBoard() {
    SquareReversiModel m = new SquareReversiModel(8);
    m.startGame();
    SquareReversiTextualView view = new SquareReversiTextualView(m);
    Assert.assertEquals(view.printBoard(), "");
  }

}
