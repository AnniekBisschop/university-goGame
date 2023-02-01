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
    int[] rowStep = {-1, 0, 1, 0};
    int[] colStep = {0, 1, 0, -1};
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

    //Own color = white
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

    public void gameOver() {

    }

//    public boolean isCaptured(int row, int column) {
//        //sets the value of color to the result of the board.getStones(row, column)
//        char color = board.getStones(row, column);
//        if (color == EMPTY) {
//            return false;
//        }
//        //If "color" is equal to BLACK, opponentColor will be assigned the value WHITE.
//        char opponentColor = (color == BLACK) ? WHITE : BLACK;
//        //The values in the arrays correspond to the movement direction: -1 in the row direction means moving up,
//        // 1 means moving down, -1 in the column direction means moving left, and 1 means moving right.
//
//        boolean isCaptured = true;
//        //The check is performed by iterating over 4 neighboring positions (up, right, down, left)
//        // using the rowStep and colStep arrays, and checking if each neighbor is within the board limits and has the opponent color.
//        // If any of the neighbors does not have the opponent color, or is not within the board limits,
//        // isCaptured is set to false and the loop breaks. Finally, the function returns isCaptured.
//        for (int i = 0; i < 4; i++) {
//            //adds the rowStep[i] to the row to get the row index of the neighbor and
//            // adds the colStep[i] to the column to get the column index of the neighbor.
//            int rowNeighbor = row + rowStep[i];
//            int colNeighbor = column + colStep[i];
//            if (rowNeighbor >= 0 && rowNeighbor < BOARD_SIZE && colNeighbor >= 0 && colNeighbor < BOARD_SIZE) {
//                if (board.getStones(rowNeighbor, colNeighbor) != opponentColor) {
//                    isCaptured = false;
//                    break;
//                }
//            }
//            //In the else block, the isCaptured variable is set to false. This is because if the neighboring cell is
//            // outside of the bounds of the board, it cannot be the same color as the opponent and therefore the stone at the
//            // original cell cannot be captured.
//
//            else {
//                isCaptured = false;
//                break;
//            }
//        }
//        //If the loop completes and isCaptured is still true, the function returns isCaptured,
//        // indicating that the stone is captured.
//        return isCaptured;
//    }
//
//    public void capture(int row, int column) {
//        if (isCaptured(row, column)) {
//            board.setStones(row, column, EMPTY);
//            for (int i = 0; i < 4; i++) {
//                int rowNeighbor = row + rowStep[i];
//                int colNeighbor = column + colStep[i];
//                if (rowNeighbor >= 0 && rowNeighbor < BOARD_SIZE && colNeighbor >= 0 && colNeighbor < BOARD_SIZE) {
//                    //recursion keep checking
//                    capture(rowNeighbor, colNeighbor);
//                }
//            }
//        }
//    }





}
