package cs3500.reversi.model;


/**
 * Represents the Reversi interface.
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * Starts the game with the initial board state using an axial coordinate system.
   */
  void startGame();

  /**
   * Makes a "move" for either Black or White's turn.
   * @param currentCell given cell
   */
  void makeMove(Cell currentCell);

  /**
   * Passes the turn.
   */
  void passTurn();

  void addListener(ModelListeners listeners);

}
