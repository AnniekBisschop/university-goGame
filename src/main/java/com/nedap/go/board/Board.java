package com.nedap.go.board;

import java.util.Scanner;

public class Board {

    public static final int BOARD_SIZE = 19;
    private static final char BLACK = 'B';
    private static final char WHITE = 'W';
    public static final char EMPTY = '.';
    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initializeBoard();

        boolean blackTurn = true;
        boolean gameOver = false;
        while (!gameOver) {
            printBoard();
            int x, y;
            System.out.print("Enter a row (x coordinate) (1-" + BOARD_SIZE + "): ");
            x = scanner.nextInt() - 1;
            System.out.print("Enter a column (y coordinate) (1-" + BOARD_SIZE + "): ");
            y = scanner.nextInt() - 1;

            if (blackTurn) {
                board[x][y] = BLACK;
            } else {
                board[x][y] = WHITE;
            }
            blackTurn = !blackTurn;
        }

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
                System.out.printf("%-3s", board[row][column]);
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
                board[row][column] = EMPTY;
            }
        }
    }

    //added getter for Junit test
    public static char[][] getBoard() {
        return board;
    }
}




