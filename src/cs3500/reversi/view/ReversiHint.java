package cs3500.reversi.view;

import java.awt.*;
import java.awt.geom.Point2D;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.Disk;
import cs3500.reversi.model.ReadOnlyReversiModel;

public class ReversiHint extends ReversiPanel implements ReversiView {
  private boolean hintsEnabled;
  private ReadOnlyReversiModel model;

  public ReversiHint(ReadOnlyReversiModel model) {
    super(model);
    this.model = model;
    this.hintsEnabled = false;

  }

  public void enableHints(boolean enable) {
    hintsEnabled = enable;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (hintsEnabled) {
      System.out.println("inside drawhints method");
      drawHints((Graphics2D) g);
    }
  }

  private void drawHints(Graphics2D g2d) {
    int boardSize = model.getBoardSize();
    int frameSize = 750;
    int hexSize = frameSize / ((2 * boardSize - 1) * 2);
    double hexWidth = Math.sqrt(3) * hexSize;
    double hexHeight = 3.0 / 2.0 * hexSize;

    for (int r = -boardSize + 1; r < boardSize; r++) {
      int q1 = Math.max(-boardSize + 1, -boardSize + 1 - r);
      int q2 = Math.min(boardSize, -r + boardSize);

      for (int q = q1; q < q2; q++) {
        Point2D.Double pixelCenter = hexToPixel(q, r, hexWidth, hexHeight);
        int[] xCorners = new int[6];
        int[] yCorners = new int[6];

        for (int i = 0; i < 6; i++) {
          double angle = Math.PI / 3.0 * (i + 0.5);
          xCorners[i] = (int) (pixelCenter.getX() + hexSize * Math.cos(angle));
          yCorners[i] = (int) (pixelCenter.getY() + hexSize * Math.sin(angle));
        }

        if (model.isMoveLegal(new Cell(new CellCoordinate(q, r), Disk.BLACK))) {
          g2d.setColor(Color.green);
          g2d.drawPolygon(xCorners, yCorners, 6);
          g2d.setColor(new Color(0, 128, 0, 50));
          g2d.fillPolygon(xCorners, yCorners, 6);
        }
      }
    }
  }

  private Point2D.Double hexToPixel(int q, int r, double hexWidth, double hexHeight) {
    double x = hexWidth * (q + r / 2.0);
    double y = hexHeight * r;
    return new Point2D.Double(x, y);
  }


  @Override
  public void repaintView() {
    repaint();
  }
}
