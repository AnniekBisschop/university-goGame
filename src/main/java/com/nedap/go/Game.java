package com.nedap.go;

import com.nedap.go.board.Board;
import com.nedap.go.client.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static com.nedap.go.board.Board.*;
import static com.nedap.go.Protocol.*;

/**
 * The game class implements the rules of the Go game
 * */

public class Game {

    private Board board;

    // ArrayList to store previous states of the board
    private static ArrayList<String> boardHistory;

    private  Player player1;
    private Player player2;
    private Player currentPlayer;
    private static int amountPasses = 0;

    /**
     * Constructor name: Game
     * @param player1 (Player)
     * @param player2 (Player)
     * Inside the function:
     * 1. Creates a new game with 2 players and a board
     * */
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
     * */
    public void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }}
//TODO: Write more logic to check valid move
    //TODO write JAVADOC
    public void doMove(int row, int col, char color){
        //update the board with move
        if (!isKo(row, col, color)) {
            board.placeStone(row,col,color);
            Board copyBoard = board.copyBoard();
            boardHistory.add(board.toString());
            switchPlayer();
        } else {
            //TODO: Make communication go through gamehandler
            currentPlayer.sendMessageToClient(INVALIDMOVE + SEPARATOR + currentPlayer.getUsername() + SEPARATOR + "spot taken, not a valid position or ko-rule violated");
            currentPlayer.sendMessageToClient(YOURTURN);
        }
        if(isCaptured(row,col)){
            //Change name??? -> Empty fields
            capture(row,col);
            currentPlayer.sendMessageToClient("CAPTURE");
        }
        //TODO if captured
        //TODO removeStones
        ///TODO Resetpass too much? PLacestone has resetPass
        resetPass();
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
//    public boolean isCaptured(int row, int column){
//        //get color
//        char color = board.getStones(row, column);
//        char opponentColor = (color == BLACK) ? WHITE : BLACK;
//        if (color == EMPTY || color != opponentColor) {
//            return false;
//        }
//
//        //If all four neighboring spots contain the opponent's color, the code returns true. Otherwise, it returns false.
//        if ((row > 0 && board.getStones(row-1, column) == opponentColor) &&
//                (row < BOARD_SIZE-1 && board.getStones(row+1, column) == opponentColor) &&
//                (column > 0 && board.getStones(row, column-1) == opponentColor) &&
//                (column < BOARD_SIZE-1 && board.getStones(row, column+1) == opponentColor)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean isCaptured(int row, int column) {
        //get color
        char color = board.getStones(row, column);

        //empty spot cannot be captured
        if (color == EMPTY) {
            return false;
        }
        if (hasLiberties(row, column)) return false;


        //if no liberties, check neighboring spots recursively


        //checks spot one row above the current spot (row-1) contains a stone of the same color as the current spot.
        // If it does, the function calls isCaptured with the coordinates of that spot as arguments.
        if (row > 0 && board.getStones(row-1, column) == color) {
            if (!isCaptured(row-1, column)) {
               // returns false. This is because if a neighboring spot contains a stone of the same color
                return false;
            }
        }

        //This checks if the spot one row below the current spot (row+1) contains a stone of the same color as the current spot.
        if (row < BOARD_SIZE-1 && board.getStones(row+1, column) == color) {
            if (!isCaptured(row+1, column)) {
                return false;
            }
        }

        //spot one column to the left of the current spot (column-1) contains a stone of the same color as the current spot.
        if (column > 0 && board.getStones(row, column-1) == color) {
            if (!isCaptured(row, column-1)) {
                return false;
            }
        }

        //spot one column to the right of the current spot (column-1) contains a stone of the same color as the current spot.
        if (column < BOARD_SIZE-1 && board.getStones(row, column+1) == color) {
            if (!isCaptured(row, column+1)) {
                return false;
            }
        }
        return true;
    }

    //check if the group has liberties
    private boolean hasLiberties(int row, int column) {
        //checks if the spot one row above the current spot (row-1) is empty
        if (row > 0 && board.getStones(row -1, column) == EMPTY) {
            return true;
        }
        //checks if the spot one row below the current spot (row+1) is empty
        if (row < BOARD_SIZE-1 && board.getStones(row +1, column) == EMPTY) {
            return true;
        }

        //checks if the spot one column to the left of the current spot (column-1) is empty.
        if (column > 0 && board.getStones(row, column -1) == EMPTY) {
            return true;
        }
        //checks if the spot one column to the right of the current spot (column-1) is empty.
        if (column < BOARD_SIZE-1 && board.getStones(row, column +1) == EMPTY) {
            return true;
        }
        return false;
    }


    //Waar is black omsingeld door wit = groep zwart gecaptured
    // loop door board
    // is eigen kleur of leeg -> continue (lijst met eigen kleur of leeg? indexes)

    //-> niet captured
    //check liberties --> niet caputured

    //Als bovenstaande acties allemaal false zijn --> mogelijkheid tot captured
    //check alle buren. zodra in van de buren een opponentcolor --> voeg toe aan lijst mogelijkecaptures
    //roep zelfde functie aan voor alle buren --> als libertie -> break, anders weer toevoegen mogelijke captures
    //


    // of buurman zwart --> niet captured


    //wat te doen met de randen? return -1? Dit is buiten het board. getstones. check waarde. binnen board? zo niet  -1.



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

    public static int getAmountPasses() {
        return amountPasses;
    }

    public String printCurrentBoard() {
        // Play the game here
        return board.printBoard();
        // Continue the game logic
    }

    public void getWinner(){

    }

    public Board getBoard() {
        return board;
    }

    public void gameOver() {

    }

}
