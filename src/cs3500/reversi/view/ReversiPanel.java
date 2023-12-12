package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;


import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.Disk;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.strategy.MaxCaptureStrategy;

/**
 * Represents a panel in the gema of reversi.
 */
public class ReversiPanel extends JPanel {
  private final ReadOnlyReversiModel model;

  private ViewFeatures vf;

  private ViewListeners vl;

  // Intentionally set as 1000 to be completely off the board.
  // If not set, the highlighted cell will start at (0,0).
  private int selectedQ = 1000;

  // Intentionally set as 1000 to be completely off the board.
  // If not set, the highlighted cell will start at (0,0).
  private int selectedR = 1000;

  private boolean hintsEnabled;




  /**
   * constructs a panel in the game of reversi.
   *
   * @param model the provided model.
   */
  public ReversiPanel(ReadOnlyReversiModel model) {
    super();
    this.model = model;
    this.hintsEnabled = false;

    addMouseListener(new MouseEventsListener());
    setFocusable(true);
    requestFocusInWindow();
    requestFocus();

    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW)).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pressed");
    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW)).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed");
    getActionMap().put("pressed", new KeyEventsListener());
    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW)).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "pressed");

  }

  public void setFeaturesListener(ViewFeatures vf) {
    this.vf = vf;
  }

  @Override
  public void paintComponent(Graphics g) {
    int boardSize = model.getBoardSize();
    int frameSize = 750;
    Graphics2D g2d = (Graphics2D) g;
    int hexSize = frameSize / ((2 * boardSize - 1) * 2);
    double hexWidth = Math.sqrt(3) * hexSize;
    double hexHeight = 3.0 / 2.0 * hexSize;
    g2d.translate(frameSize / 2, frameSize / 2);
    setBackground(Color.DARK_GRAY);

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

        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xCorners, yCorners, 6);

        if (q == selectedQ && r == selectedR) {
          if (hintsEnabled) {
                int flippedDiscs = model.countFlippedDiscs(model.getCell(new CellCoordinate(q,r)));
                g2d.setColor(Color.BLACK); // Change color as needed

                System.out.println(flippedDiscs);
                g2d.drawString(String.valueOf(flippedDiscs),
                        (int) pixelCenter.getX(), (int) pixelCenter.getY());
              }

          g2d.setColor(Color.cyan);
          g2d.fillPolygon(xCorners, yCorners, 6);
        } else {
          g2d.setColor(Color.lightGray);
          g2d.fillPolygon(xCorners, yCorners, 6);
        }

        double diskDiameter = hexWidth * 0.6;
        Disk cellDisk = model.getCell(new CellCoordinate(q, r)).getDisk();
        if (cellDisk == Disk.WHITE) {
          g2d.setColor(Color.WHITE);
          g2d.fillOval((int) (pixelCenter.getX() - diskDiameter / 2),
                  (int) (pixelCenter.getY() - diskDiameter / 2),
                  (int) diskDiameter, (int) diskDiameter);
        }

        if (cellDisk == Disk.BLACK) {
          g2d.setColor(Color.BLACK);
          g2d.fillOval((int) (pixelCenter.getX() - diskDiameter / 2),
                  (int) (pixelCenter.getY() - diskDiameter / 2),
                  (int) diskDiameter, (int) diskDiameter);
        }
      }
    }
    repaint();
  }

  private Point2D.Double hexToPixel(int q, int r, double hexWidth, double hexHeight) {
    double x = hexWidth * (q + r / 2.0);
    double y = hexHeight * r;
    return new Point2D.Double(x, y);
  }

  public void displayErrorMessage(IllegalArgumentException e) {
    JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void displayErrorStateMessage(IllegalStateException e) {
    JOptionPane.showMessageDialog(null, e.getMessage(),
            "Game Over", JOptionPane.ERROR_MESSAGE);
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int boardSize = model.getBoardSize();
      int frameSize = 750;
      int hexSize = frameSize / ((2 * boardSize - 1) * 2);
      double hexWidth = Math.sqrt(3) * hexSize;
      double hexHeight = 3.0 / 2.0 * hexSize;
      int mouseX = e.getX() - frameSize / 2;
      int mouseY = e.getY() - frameSize / 2;

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

          Polygon hexagon = new Polygon(xCorners, yCorners, 6);
          if (hexagon.contains(mouseX, mouseY)) {
            if (q == selectedQ && r == selectedR) {
              selectedQ = 1000;
              selectedR = 1000;
            } else {
              selectedQ = q;
              selectedR = r;
              System.out.println("Cell clicked: (" + q + ", " + r + ")");
            }
            if (vl != null) {
              vl.addViewListerners(vf);
            }
            repaint();
            return;
          }
        }
      }
    }
  }

  private class KeyEventsListener extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("P") || e.getActionCommand().equals("p")) {
        this.handlePass();
      }

      if (Objects.equals(e.getActionCommand(), "\n")) {
        this.handleMove();
      }

      if (e.getActionCommand().equals("H") || e.getActionCommand().equals("h")) {
        this.handleHint();
      }
    }


    private void handleMove() {
      if (selectedQ != 1000 && selectedR != 1000) {
        if (vf != null) {
          System.out.println("Move selected: (" + selectedQ + ", " + selectedR + ")");
          vf.handleMove(model.getCell(new CellCoordinate(selectedQ, selectedR)));
        }
      }
    }

    private void handlePass() {
      if (vf != null) {
        vf.handlePass();
      }
    }

    private void handleHint() {
      enableHint();
      if (vf != null) {
        System.out.println("hints enabled");
        vf.handleHint();
      }
    }
  }

  public void setViewListener(ViewListeners listener) {
    this.vl = listener;
  }

  public void enableHint() {
    this.hintsEnabled = true;
  }
}










