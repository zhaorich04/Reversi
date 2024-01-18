Reversi Overview

Our file implements a model for the board game Reversi.
The model tracks the state of a Reversi game, enforcing the rules of valid moves and detecting
when the game is over.

The code is designed to be extensible to support different representations of the board
(textual, graphical, etc.) by implementing the ReversiModel interface. The current implementation
uses a BasicModel class that stores the board state in a HashMap.

Quick Start
Here is an example of how to create a Reversi game and make a move:
ReversiModel model = new BasicModel(8);
model.makeMove(new Cell(new CellCoordinate(1, -2)));

Key Components
ReversiModel - Interface defining mutator methods on a Reversi game model
ReadOnlyReversiModel - Interface defining observation methods for a Reversi game model.
BasicModel - Implementation of ReversiModel that tracks game state
Cell - Represents a cell on the Reversi board
CellCoordinate - Represents (q,r) coordinates for a cell in which the represents the column and row
in a hexagonal grid, respectfully.
The BasicModel contains the core game logic and drives the control flow by validating moves and
detecting end-game. The Cell and CellCoordinate classes provide data structures to represent the
board grid.
Player - While we did not fully implement the Player interface, the model would take in the player
and the player would only have access to makeMove and passTurn.
ReversiStrategy - Interface used to implement different strategies for an AI player.
ReversiRunner - runs the game using the gui view and the model.
ReversiPanel - JPanel representing the Reversi game with subclasses such as MousePressed and
KeyPressed which handles user input. If the user presses 'ENTER', the move is made at the cell
selected by the user, while the key 'P', the user passes. The controller will make this functional.
ReversiGraphicsView - JFrame representing the Reversi game utilizing a JPanel of the game.
ReversiView - Interface representing the Reversi game as a JFrame.

ModelFeatures - Interface used to represent the ModelFeatures of the Reversi game
ModelListeners - Interface used to represent the ModelListeners of the Reversi game that connects
the model to the controller
ViewFeatures - Interface used to represent the ViewFeatures of the Reversi game
ViewListeners - Interface used to represent the ViewListeners of the Reversi game that connects
the view to the controller

AIPlayer - class representing an AIPlayer of the Reversi game
HumanPlayer - class representing a human player of the Reversi game
Player - interface representing a player of the Reversi game



Source Organization
model - Contains model interfaces and implementation
view - Contains textual and gui view implementation
strategy - Contains the strategy interface with the chooseCell method to be implemented in the
strategy classes.
controller - Contains the controller interface and implementation
The model classes define the core game logic. The view creates textual representations of the board.
The controller connects the player to play the game of Reversi (passing, making a move on the board)

Changes for Part 2
getWhiteScore() - this was missing because we previously did not need to track the game score.
getBlackScore() - this was missing because we previously did not need to track the game score.
This allowed for the getWinner() method to be refactored to use the score methods.
We also moved the getWinner() method from the ReversiModel interface to the ReadOnlyReversiModel
interface.

hasLegalMove() - this is needed to ensure that the current player has a legal move.
isMoveLegal() - this was not previously added to our interface
isGameOver() - this was not previously added to our interface
getBoardSize() - this is needed to retrieve the given board size for the Reversi game
copyBoard() - this is needed to create a copy of the board state
getGameBoard() - this is needed to retrieve the hashMap representing the Reversi game board
All of the above observation methods were placed in the ReadOnlyReversiModel interface to implement
the strategies needed for the game.

Our ReversiTextualView now takes in a ReadOnlyReversiModel instead of a BasicModel.

Changes for Part 3
notifyAction() - this is needed to ensure that all the listeners in ModelListeners are notified of
a change
addListener(ModelListeners listeners) - this is needed to add listeners to the ModelListeners
waiting for a change
isGameOver() - changes were made to determine when the game is over (when all the white or black
disks are on the board and there are no valid moves for the opposite player)
copyBoard() - the board copy is now a deep copy
our board is now represented as a Map<CellCoordinate, Cell> instead of a
HashMap<CellCoordinate, Cell>
startGame() - this is needed to ensure that our reversi game is started with the initial board state
using an axial coordinate system and turns the gameStarted boolean flag to be true

Controller - class representing a controller of the Reversi game in which handles moves and passes
of the game
ReversiController - Interface used to represent the Controller of the Reversi game

ModelFeatures - Interface used to represent the ModelFeatures of the Reversi game
ModelListeners - Interface used to represent the ModelListeners of the Reversi game that connects
the model to the controller
ViewFeatures - Interface used to represent the ViewFeatures of the Reversi game
ViewListeners - Interface used to represent the ViewListeners of the Reversi game that connects
the view to the controller

AIPlayer - class representing an AIPlayer of the Reversi game
With our current implementation, our AIPlayer does make valid strategy moves when the board is
clicked
HumanPlayer - class representing a human player of the Reversi game
Player - interface representing a player of the Reversi game

Changes for Part 4
Readme updates

We were able to write an adapter for their IBoard interface to perform actions on our ReversiModel 
interface, an adapter for transforming our Disk enum into their CellColor enum and vice versa, an 
adapter for our ModelListeners to their ModelStatus, an adapter for our Player interface to their 
IPlayer interface, ande an adapter for our ReversiStrategy interface to their ReversiStrategies 
interface. Our JAR file and command line does not fully work for the providers’ code. We didn’t 
adapt the views.
