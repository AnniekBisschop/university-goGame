package com.nedap.go;

import com.nedap.go.board.Board;
import com.nedap.go.client.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static com.nedap.go.board.Board.*;

/**
 * The game class implements the rules of the Go game
 * */

public class Game {

    private Board board;

    // ArrayList to store previous states of the board
    private static ArrayList<String> boardHistory;

    private  Player playerBlack;
    private Player playerWhite;
    private Player currentPlayer;
    private static int amountPasses = 0;

    /**
     * Constructor name: Game
     * @param playerBlack (Player)
     * @param playerWhite (Player)
     * Inside the function:
     * 1. Creates a new game with 2 players and a board
     * */
    public Game(Player playerBlack, Player playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        currentPlayer = playerBlack;
        this.board = new Board();
        this.boardHistory = new ArrayList<>();
    }

    /**
     * Function name: switchPlayer
     * Inside the function:
     * 1. players take turn, alternation of player
     * */
    public void switchPlayer() {
        if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        } else {
            currentPlayer = playerBlack;
        }
    }
//TODO: Write more logic to check valid move
    //TODO write JAVADOC
    public void doMove(int row, int col, char color){
        //update the board with move
        if (!isKo(row, col, color)) {
            board.placeStone(row,col,color);
            Board copyBoard = board.copyBoard();
            boardHistory.add(board.toString());
        }
        if(isCaptured(row,col)){
            //Change name??? -> Empty fields
            capture(row,col);
        }
        //TODO if captured
        //TODO removeStones
        ///TODO Resetpass to much? PLacestone has resetPass
        resetPass();
        switchPlayer();
    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.
    // Check if the move would violate the rule of ko



    //TODO: JAVADOC

   public boolean isKo(int row, int col, char color){
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
    public boolean isCaptured(int row, int column){
        char color = board.getStones(row, column);
        if (color == EMPTY) {
            return false;
        }
        //check the color of the opponent
        char opponentColor = (color == BLACK) ? WHITE : BLACK;
        if ((row > 0 && board.getStones(row-1, column) == opponentColor) &&
                (row < BOARD_SIZE-1 && board.getStones(row+1, column) == opponentColor) &&
                (column > 0 && board.getStones(row, column-1) == opponentColor) &&
                (column < BOARD_SIZE-1 && board.getStones(row, column+1) == opponentColor)) {
            return true;
        } else {
            return false;
        }
    }

    public void capture(int row, int column) {
        //stones captured and removed from the board
        if (isCaptured(row, column)) {
            board.setStones(row, column, EMPTY);
        }
    }

    //Scoring: The number of captured stones and territory must be accurately calculated at the end of the game.

    //End of Game: The game ends when both players pass consecutively.
    public boolean isGameOver() {
        if (amountPasses == 2) {
            return true;
        }else {
            return false;
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }
//TODO: check need this??
    public Player getTurn() {
        return currentPlayer;
    }
}
