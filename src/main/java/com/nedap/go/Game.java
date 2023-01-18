package com.nedap.go;

import com.nedap.go.board.Board;
import com.nedap.go.client.Player;

import static com.nedap.go.board.Board.*;

/**
 * The game class implements the rules of the Go game
 * */

public class Game {

    public Board board;
    private static Player playerBlack;
    private static Player playerWhite;
    private static Player currentPlayer;
    private static int amountPasses = 0;

    /**
     * Constructor name: Game
     * @param playerBlack (Player)
     * @param playerWhite (Player)
     * @param board (Board)
     * Inside the function:
     * 1. Creates a new game with 2 players and a board
     * */
    public Game(Player playerBlack, Player playerWhite, Board board) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        currentPlayer = playerBlack;
        this.board= board;
    }

    /**
     * Function name: switchPlayer
     * Inside the function:
     * 1. players take turn, alternation of player
     * */
    public static void switchPlayer() {
        if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        } else {
            currentPlayer = playerBlack;
        }
    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.

    //Illegal moves: A player cannot place a stone on a point that is already occupied or that would violate any of the other rules.

    //Pass: A player can choose to pass their turn if they do not want to make a move.
    /**
     * Function name: pass
     * Inside the function:
     * 1. Checks if the players played 2 consecutive passes. This will end the game.
     */
    public static void pass() {
        if (amountPasses == 2) {
            System.out.println("Both players have passed, the game is over.");
            gameOver();
        } else {
            // switch the current player
           switchPlayer();
            amountPasses++;
        }
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
     * @param row (int)
     * @param column (int)
     * @return boolean
     *
     * Inside the function:
     * 1. It checks if the stone is empty or not, if it's empty it returns false.
     * 2. Then it checks the color of the stone and the color of the opponent.
     * 3. It then checks if the stone is surrounded by opponent's stone by checking if all the four positions orthogonally
     * 4. If it is surrounded, it returns true
     * */
    public Boolean isCaptured(int row, int column){
        char color = board.getColor(row, column);
        if (color == EMPTY) {
            return false;
        }
        //check the color of the opponent
        char opponentColor = (color == BLACK) ? WHITE : BLACK;
        if ((row > 0 && board.getColor(row-1, column) == opponentColor) &&
                (row < BOARD_SIZE-1 && board.getColor(row+1, column) == opponentColor) &&
                (column > 0 && board.getColor(row, column-1) == opponentColor) &&
                (column < BOARD_SIZE-1 && board.getColor(row, column+1) == opponentColor)) {
            return true;
        } else {
            return false;
        }
    }

    public void capture(int row, int column) {
        //stones captured and removed from the board
        if (isCaptured(row, column)) {
            board.setColor(row, column, EMPTY);
        }
    }

    //Scoring: The number of captured stones and territory must be accurately calculated at the end of the game.

    //checkBoard is full()

    //End of Game: The game ends when both players pass consecutively.
    public static boolean isGameOver() {
        return true;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

}
