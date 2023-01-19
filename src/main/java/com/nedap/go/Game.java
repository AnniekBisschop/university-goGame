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

    public Board board;

    // ArrayList to store previous states of the board
    private static ArrayList<Board> boardHistory;

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
        this.boardHistory = new ArrayList<>();
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
//TODO: Write more logic to check valid move
    //TODO write JAVADOC
    public void doMove(int row, int col, char color){
        //update the board with move
//        if (!isKo(row, col, color)) {
            board.placeStone(row,col,color);
            Board copyBoard = board.copyBoard();
            boardHistory.add(copyBoard);
//        }

        switchPlayer();
    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.
    // Check if the move would violate the rule of ko


//TODO: compare arraylist deepcopy with arraylist current board boardRepresentation
    //TODO: JAVADOC
//TODO: Create test for isKo();
   public boolean isKo(int row, int col, char color){
       Board boardAfterMove = board.copyBoard();
       boardAfterMove.placeStone(row, col, color);

       for (Board historyBoard : boardHistory) {
           if (historyBoard.equals(boardAfterMove)) {
               System.out.println("The current board is the same as a previous board in the history.");
               return true;
           }
       }
       System.out.println("The current board is different from the previous board in the history.");
       return false;
   }

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

    //checkBoard is full()

    //End of Game: The game ends when both players pass consecutively.
    public static boolean isGameOver() {
        return true;
    }

    public static void gameOver(){
        //TODO: write method
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getTurn() {
        return currentPlayer;
    }



//    public static void main(String[] args) {
//        Player playerBlack = new Player("playerBlack");
//        Player playerWhite = new Player("playerWhite");
//        Board board = new Board();
//        Game game = new Game(playerBlack,playerWhite,board);
//        game.doMove(1,1,BLACK);
//        game.doMove(4,8, WHITE);
////        board.printBoard();
//        game.doMove(6,8, BLACK);
////        board.printBoard();
//        game.doMove(3,8, WHITE);
////        board.printBoard();
//        System.out.println("*********************");
//        game.doMove(1,3, BLACK);
////        board.printBoard();
//        game.doMove(2,5, WHITE);
////        board.printBoard();
//        game.doMove(2,5, BLACK);
//        board.printBoard();
//    }

}
