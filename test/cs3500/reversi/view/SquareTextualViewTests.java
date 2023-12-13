package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicModel;

public class SquareTextualViewTests {
  @Test
  public void testInitialBoard() {
    BasicModel m = new BasicModel(8);
    m.startGame();
    SquareReversiTextualView view = new SquareReversiTextualView(m);
    Assert.assertEquals(view.printBoard(), "");
  }

}
