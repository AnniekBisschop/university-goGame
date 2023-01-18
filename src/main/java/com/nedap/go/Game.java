package com.nedap.go;

import com.nedap.go.board.Board;
import com.nedap.go.client.Player;

import java.util.ArrayList;

import static com.nedap.go.board.Board.*;

/**
 * The game class implements the rules of the Go game
 * */

public class Game {

    public Board board;

    // ArrayList to store previous states of the board
    private ArrayList<Board> boardHistory;

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
//TODO: Write more logic to check valid move
    //TODO write JAVADOC
    public void doMove(int row, int col){
        board.placeStone(row,col,WHITE);
        //update the board with move
//        boardHistory.add(board);

        switchPlayer();
    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.
    // Check if the move would violate the rule of ko


//TODO: compare arraylist deepcopy with arraylist current board boardRepresentation
    //TODO: JAVADOC
//TODO: Create test for isKo();
   public boolean isKo(){
       Board currentBoard = copyBoard();
       //checks if the boardHistory ArrayList has at least one element
       if (boardHistory.size() >= 1) {
           boolean isKo = true;
           //boardHistory.get(boardHistory.size() - 1) is used to access the last element of the boardHistory ArrayList,
           //this is the previous board state.
           Board previousBoard = boardHistory.get(boardHistory.size() - 1);
           for (int i = 0; i < board.BOARD_SIZE; i++) {
               for (int j = 0; j < board.BOARD_SIZE; j++) {
                   if (currentBoard.getStones(i,j) != previousBoard.getStones(i,j)) {
                       isKo = false;
                       break;
                   }
               }
           }
           if (isKo) {
               System.out.println("Invalid move: Ko rule violated!");
               return true;
           }
       }
       return true;
   }

    //TODO: check why copyboard is not working yet.
    //Should copy be done in board class?
    public Board copyBoard() {
        // Create a new Board object to store the copy
        Board copyBoard = new Board();
        // Iterate over the rows and columns of the original board
        //In each iteration, it copies the stone at the current position to the corresponding
        //position on the copy board by calling setStones(row, column, board[row][column]).
        //TODO: CHeck if this works correctly

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                // Copy the stone at the current position to the copy board
                copyBoard.setStones(row, column, board.getStones(row,column));
            }
        }
        return copyBoard;
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



    public static void main(String[] args) {
        Player playerBlack = new Player("playerBlack");
        Player playerWhite = new Player("playerWhite");
        Board board = new Board();
        Game game = new Game(playerBlack,playerWhite,board);
        game.doMove(4,8);
        game.doMove(6,8);
        game.doMove(3,8);
        board.printBoard();
        game.doMove(1,3);
        game.doMove(2,5);
        board.printBoard();
    }

}
