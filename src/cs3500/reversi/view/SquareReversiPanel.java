package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.CellCoordinate;
import cs3500.reversi.model.Disk;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a panel for a game of square reversi.
 */
public class SquareReversiPanel extends JPanel {
  private final ReadOnlyReversiModel model;

  private ViewFeatures vf;

  private ViewListeners vl;

  private int selectedQ = -1;
  private int selectedR = -1;

  private boolean blackHintsEnabled;
  private boolean whiteHintsEnabled;

  /**
   * constructs a panel for a game of square reversi.
   * @param model the given model.
   */
  public SquareReversiPanel(ReadOnlyReversiModel model) {
    super();
    this.model = model;
    this.blackHintsEnabled = false;
    this.whiteHintsEnabled = false;

    addMouseListener(new MouseEventsListener());
    setFocusable(true);
    requestFocusInWindow();
    requestFocus();

    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW))
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pressed");
    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW))
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed");
    getActionMap().put("pressed", new KeyEventsListener());
    getInputMap((JComponent.WHEN_IN_FOCUSED_WINDOW))
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "pressed");
  }

  public void setFeaturesListener(ViewFeatures vf) {
    this.vf = vf;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int boardSize = model.getBoardSize();
    int frameSize = 750;
    int cellSize = frameSize / boardSize;

    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        int x = col * cellSize;
        int y = row * cellSize;

        g.setColor(Color.lightGray);

        if (col == selectedQ && row == selectedR) {
          g.setColor(Color.cyan);
        }

        g.fillRect(x, y, cellSize, cellSize);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize);

        Cell cell = model.getCell(new CellCoordinate(col, row));
        drawDisk(g, cell, x, y, cellSize);
      }
    }
  }

  private void drawDisk(Graphics g, Cell cell, int x, int y, int cellSize) {
    Disk cellDisk = cell.getDisk();
    int diskDiameter = (int) (cellSize * 0.8);

    if (cellDisk == Disk.WHITE) {
      g.setColor(Color.WHITE);
      g.fillOval(x + (cellSize - diskDiameter) / 2, y + (cellSize - diskDiameter) / 2,
              diskDiameter, diskDiameter);
    } else if (cellDisk == Disk.BLACK) {
      g.setColor(Color.BLACK);
      g.fillOval(x + (cellSize - diskDiameter) / 2, y + (cellSize - diskDiameter) / 2,
              diskDiameter, diskDiameter);
    }
  }



  public void displayErrorMessage(IllegalArgumentException e) {
    JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void displayErrorStateMessage(IllegalStateException e) {
    JOptionPane.showMessageDialog(null, e.getMessage(),
            "Game Over", JOptionPane.ERROR_MESSAGE);
  }

  private class MouseEventsListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int boardSize = model.getBoardSize();
      int frameSize = 750;

      int cellSize = frameSize / boardSize;

      int mouseX = e.getX();
      int mouseY = e.getY();

      int col = mouseX / cellSize;
      int row = mouseY / cellSize;

      if (col >= 0 && col < boardSize && row >= 0 && row < boardSize) {
        if (col == selectedQ && row == selectedR) {
          // Toggle the color if clicked again
          selectedQ = -1;
          selectedR = -1;
        } else {
          selectedQ = col;
          selectedR = row;
        }

        if (vl != null) {
          vl.addViewListerners(vf);
        }

        repaint();
      }
    }
  }




  private class KeyEventsListener extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("P") || e.getActionCommand().equals("p")) {
        handlePass();
      }

      if (Objects.equals(e.getActionCommand(), "\n")) {
        handleMove();
      }

      if (e.getActionCommand().equals("H") || e.getActionCommand().equals("h")) {
        handleHint();
      }
    }

    private void handleMove() {
      if (selectedQ != -1 && selectedR != -1) {
        if (vf != null) {
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
        vf.handleHint();
      }
    }
  }

  public void setViewListener(ViewListeners listener) {
    this.vl = listener;
  }

  public void enableHint() {
    toggleHints(model.getIsBlacksTurn());
  }

  /**
   * Turns hints on or off for a black or white player.
   * @param isBlack true if its the black player.
   */
  public void toggleHints(boolean isBlack) {
    if (isBlack) {
      blackHintsEnabled = !blackHintsEnabled;
    } else {
      whiteHintsEnabled = !whiteHintsEnabled;
    }
    repaint();
  }
}

