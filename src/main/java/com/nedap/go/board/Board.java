package com.nedap.go.board;

import com.nedap.go.Game;

import java.util.Scanner;

public class Board {

    public static final int BOARD_SIZE = 9;
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
    public static final char EMPTY = '.';
    private static char[][] boardRepresentation = new char[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        initializeBoard();
    }

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initializeBoard();


        boolean blackTurn = true;
        boolean gameOver = false;
        while (!gameOver) {
            printBoard();
            System.out.println("Current player: " + Game.getCurrentPlayer());
            System.out.println("1. Make move\n");
            System.out.println("2. Pass\n");
            int choice = scanner.nextInt();
            if (choice == 1) {
                printBoard();
                int x, y;
                System.out.print("Enter a row (x coordinate) (1-" + BOARD_SIZE + "): ");
                x = scanner.nextInt() - 1;
                System.out.print("Enter a column (y coordinate) (1-" + BOARD_SIZE + "): ");
                y = scanner.nextInt() - 1;
                if (blackTurn) {
                    boardRepresentation[x][y] = BLACK;
                } else {
                    boardRepresentation[x][y] = WHITE;
                }
            } else if (choice == 2) {
                Game.pass();
                System.out.println("Current player passed, the current player is now: " + Game.getCurrentPlayer());
            }
        }
        Game.switchPlayer();
    }


    /**
     * Function name: printBoard
     * <p>
     * Inside the function:
     * 1. Prints the board with labels on x and y axes (1-BOARD_SIZE)
     */
    private static void printBoard() {
        // Print the column labels
        System.out.print(" ");
        for (int column = 0; column < BOARD_SIZE; column++) {
            System.out.printf("%3d", column + 1);
        }
        System.out.println();

        // Print the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.printf("%-3d", row + 1);
            for (int column = 0; column < BOARD_SIZE; column++) {
                System.out.printf("%-3s", boardRepresentation[row][column]);
            }
            System.out.println();
        }
    }

    /**
     * Function name: initializeBoard
     * <p>
     * Inside the function:
     * 1. Initialize the board with empty spaces represented by a "."
     */
    public static void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                boardRepresentation[row][column] = EMPTY;
            }
        }
    }

    /**
     * Function name: isEmptySpot
     *
     * @param row    (int)
     * @param column (int)
     * @return (boolean)
     * <p>
     * Inside the function:
     * 1. Returns a boolean. True when the spot is empty. False when spot is taken.
     */
    public static boolean isEmptySpot(int row, int column) {
        // check if the spot at the specified location is empty
        return boardRepresentation[row][column] == EMPTY;
    }

    /**
     * Function name: isValidPosition
     *
     * @param row    (int)
     * @param column (int)
     * @return (boolean)
     * <p>
     * Inside the function:
     * 1. Returns a boolean. True when it is a valid position. False when spot is out of bounds.
     */
    public static boolean isValidPosition(int row, int column) {
        // check if the position is within the bounds of the board
        return row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE;
    }

    /**
     * Function name: doMove
     *
     * @param row    (int)
     * @param column (int)
     * @param color  (char)
     * Inside the function:
     * 1. Checks if the stone can be placed at an empty spot within the bounds of the board.
     */
    public void doMove(int row, int column, char color) {
        if (isEmptySpot(row, column) && isValidPosition(row, column)) {
            // place a stone of the specified color at the specified location
            boardRepresentation[row][column] = color;
            Game.resetPass();
        }
    }

    /**
     * Function name: isBoardFull
     * @param board (char [][])
     * @return boolean
     * Inside the function:
     * 1. Returns boolean. True if board is full
     */
    public static boolean isBoardFull(char[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    //added getter for Junit test
    public static char[][] getBoardRepresentation() {
        return boardRepresentation;
    }

    public char getColor(int row, int column) {
        return boardRepresentation[row][column];
    }

    public void setColor(int row, int column, char color) {
        boardRepresentation[row][column] = color;
    }
}




