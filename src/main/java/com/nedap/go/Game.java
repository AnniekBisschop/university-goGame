package com.nedap.go;

import com.nedap.go.board.Board;
import com.nedap.go.client.Player;
import com.nedap.go.server.ClientHandler;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nedap.go.board.Board.*;
import static com.nedap.go.Protocol.*;

/**
 * The game class implements the rules of the Go game
 */

public class Game {

    private Board board;

    // ArrayList to store previous states of the board
    private static ArrayList<String> boardHistory;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private static int amountPasses = 0;
    private boolean[][] possibleCapture = new boolean[BOARD_SIZE][BOARD_SIZE];
    private boolean[][] visitedStones = new boolean[BOARD_SIZE][BOARD_SIZE];

    /**
     * Constructor name: Game
     *
     * @param player1 (Player)
     * @param player2 (Player)
     * Inside the function:
     * 1. Creates a new game with 2 players and a board
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        this.board = new Board();
        this.boardHistory = new ArrayList<>();
    }

    /**
     * Function name: switchPlayer
     * Inside the function:
     * 1. players take turn, alternation of player
     */
    public void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    //TODO: Write more logic to check valid move
    //TODO write JAVADOC
    public void doMove(int row, int col, char color) {
        //update the board with move
        if (!isKo(row, col, color)) {
            board.placeStone(row, col, color);
            Board copyBoard = board.copyBoard();
            boardHistory.add(board.toString());
            switchPlayer();
            resetPass();
        } else {
            //TODO: Make communication go through gamehandler
            currentPlayer.sendMessageToClient(INVALIDMOVE + SEPARATOR + currentPlayer.getUsername() + SEPARATOR + "spot taken, not a valid position or ko-rule violated");
//            currentPlayer.sendMessageToClient(YOURTURN);
        }
//        if (isCaptured(row, col)) {
//            currentPlayer.sendMessageToClient("CAPTURE");
//        }
        //TODO if captured



    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.
    // Check if the move would violate the rule of ko
    //TODO: JAVADOC
    public boolean isKo(int row, int col, char color) {
        Board boardAfterMove = board.copyBoard();
        boardAfterMove.placeStone(row, col, color);

        return boardHistory.contains(boardAfterMove.toString());
    }

    /**
     * Function name: pass
     * Inside the function:
     * 1. Checks if the players played 2 consecutive passes. This will end the game.
     */
    public void pass() {
        amountPasses++;
        switchPlayer();
    }

    /**
     * Function name: resetPass
     * Inside the function:
     * 1. Sets the amount of passes to 0
     */
    public static void resetPass() {
        amountPasses = 0;
    }


    //A stone or solidly connected group of stones of one color is captured and removed from the board
    // when all the intersections directly orthogonally adjacent to it are occupied by the opponent.

    /**
     * Function name: isCaptured
     *
     * @param row    (int)
     * @param column (int)
     * @return boolean
     * <p>
     * Inside the function:
     * 1. It checks if the stone is empty or not, if it's empty it returns false.
     * 2. Then it checks the color of the stone and the color of the opponent.
     * 3. It then checks if the stone is surrounded by opponent's stone by checking if all the four positions orthogonally
     * 4. If it is surrounded, it returns true
     */

    public boolean isCaptured(int row, int column, char color) {
        resetCaptureLists();
        return isCapturedAlgorithm(row, column, color);
    }

    public boolean isCapturedAlgorithm(int row, int column, char color) {

        //base case check to prevent stack overflow
        if (isOutOfBounds(row, column)) {
            return false;
        }

        //keeps track of visited stones
        visitedStones[row][column] = true;

        //empty spot cannot be captured
        if (color == EMPTY) {
            return false;
        }

        //if no liberties, check neighboring spots recursively
        if (hasLiberties(row, column)) {
            return false;
        }
        // Check if the current stone has already been visited
        if (!possibleCapture[row][column]) {
            // Mark the current stone as visited
            possibleCapture[row][column] = true;
        }


        //checks spot one row above the current spot (row-1) contains a stone of the same color as the current spot.
        // If it does, the function calls isCaptured with the coordinates of that spot as arguments.
        if (!isOutOfBounds(row - 1, column) && (board.getStones(row - 1, column) == color) && visitedStones[row - 1][column] == false) {
            if (!isCapturedAlgorithm(row - 1, column, color)) {
                // returns false. This is because if a neighboring spot contains a stone of the same color
                return false;
            }
        }

        //This checks if the spot one row below the current spot (row+1) contains a stone of the same color as the current spot.
        if (!isOutOfBounds(row + 1, column) && board.getStones(row + 1, column) == color && visitedStones[row + 1][column] == false) {
            if (!isCapturedAlgorithm(row + 1, column, color)) {
                return false;
            }
        }

        //spot one column to the left of the current spot (column-1) contains a stone of the same color as the current spot.
        if (!isOutOfBounds(row, column - 1) && board.getStones(row, column - 1) == color && visitedStones[row][column - 1] == false) {
            if (!isCapturedAlgorithm(row, column - 1, color)) {
                return false;
            }
        }

        //spot one column to the right of the current spot (column + 1) contains a stone of the same color as the current spot.
        if (!isOutOfBounds(row, column + 1) && board.getStones(row, column + 1) == color && visitedStones[row][column + 1] == false) {
            if (!isCapturedAlgorithm(row, column + 1, color)) {
                return false;
            }
        }

        boolean isCaptured = possibleCapture[row][column];
        return isCaptured;
    }

    /*
    * Check for the edge: When checking the neighboring stones, if any of the indices (row or column) is outside the bounds of the board,
    * then it means the group of stones is surrounded by the edge. In that case, return true (as the group is captured).*/


    //zet -> loop door board
    // zijn er omsloten groepjes van opponent color?
    //domove check of tegenstander kleur gevangen kan worden
    //TODO: test this
    public int captureOpponentStones(char ownColor) {
        ArrayList<Point2D> capturedStones = new ArrayList<>();
        int numberOfCaptures = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                char color = board.getStones(row, column);
                if (color != ownColor) {
                    if (isCaptured(row, column, color)) {
                        capturedStones.add(new Point2D.Double(row, column));
                        numberOfCaptures++;
                    }
                }
            }
        }

        for (Point2D capturedStone: capturedStones) {
            makeFieldEmptyCapture((int)capturedStone.getX(), (int)capturedStone.getY());
        }

        return numberOfCaptures;
    }

    private boolean isOutOfBounds(int row, int column) {
        return row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE;
    }

    private void resetCaptureLists(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                visitedStones[i][j] = false;
                possibleCapture[i][j] = false;
            }
        }
    }
    //check if the group has liberties
    private boolean hasLiberties(int row, int column) {
        //checks if the spot one row above the current spot (row-1) is empty
        if (row > 0 && board.getStones(row - 1, column) == EMPTY) {
            return true;
        }
        //checks if the spot one row below the current spot (row+1) is empty
        if (row < BOARD_SIZE - 1 && board.getStones(row + 1, column) == EMPTY) {
            return true;
        }

        //checks if the spot one column to the left of the current spot (column-1) is empty.
        if (column > 0 && board.getStones(row, column - 1) == EMPTY) {
            return true;
        }
        //checks if the spot one column to the right of the current spot (column-1) is empty.
        if (column < BOARD_SIZE - 1 && board.getStones(row, column + 1) == EMPTY) {
            return true;
        }
        return false;
    }


    //wat te doen met de randen? return -1? Dit is buiten het board. getstones. check waarde. binnen board? zo niet  -1.


    public void makeFieldEmptyCapture(int row, int column) {
        //stones captured and removed from the board
        board.setStones(row, column, EMPTY);
    }

    //Scoring: territory must be accurately calculated at the end of the game.


    //End of Game: The game ends when both players pass consecutively.
    public boolean isGameOver() {
        if (amountPasses == 2) {
            return true;
        } else {
            return false;
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static int getAmountPasses() {
        return amountPasses;
    }

    public String printCurrentBoard() {
        // Play the game here
        return board.printBoard();
        // Continue the game logic
    }

    public void getWinner() {

    }

    public Board getBoard() {
        return board;
    }

    public void gameOver() {

    }

}
