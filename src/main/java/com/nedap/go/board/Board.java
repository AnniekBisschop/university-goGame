package com.nedap.go.board;

import com.nedap.go.Game;

import java.util.Arrays;
import java.util.Scanner;

public class Board {

    public static final int BOARD_SIZE = 9;
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
    public static final char EMPTY = '.';
    private char[][] boardRepresentation = new char[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        initializeBoard();
    }

    /**
     * Function name: printBoard
     * <p>
     * Inside the function:
     * 1. Prints the board with labels on x and y axes (1-BOARD_SIZE)
     */
    public String printBoard() {
        StringBuilder sb = new StringBuilder();

        // Print the column labels
        sb.append(" ");
        for (int column = 0; column < BOARD_SIZE; column++) {
            sb.append(String.format("%3d", column + 1));
        }
        sb.append("\n");

        // Print the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            sb.append(String.format("%-3d", row + 1));
            for (int column = 0; column < BOARD_SIZE; column++) {
                sb.append(String.format("%-3s", boardRepresentation[row][column]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Function name: initializeBoard
     * <p>
     * Inside the function:
     * 1. Initialize the board with empty spaces represented by a "."
     */
    public void initializeBoard() {
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
    public  boolean isEmptySpot(int row, int column) {
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
     * Function name: placeStone
     *
     * @param row    (int)
     * @param column (int)
     * @param color  (char)
     * Inside the function:
     * 1. Checks if the stone can be placed at an empty spot within the bounds of the board.
     */
    public void placeStone(int row, int column, char color) {
        if (isEmptySpot(row, column) && isValidPosition(row, column)) {
            // place a stone of the specified color at the specified location
            boardRepresentation[row][column] = color;
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

    /**
     * Function name: copyBoard
     *
     * @return copyBoard
     * Inside the function:
     * 1. Create a new Board object to store the copy
     * 2. Iterate over the rows and columns of the original board
     * 3.Each iteration, it copies the stone at the current position to the corresponding position
     */
    public Board copyBoard() {
        Board copyBoard = new Board();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                // Copy the stone at the current position to the copy board
                char color = getStones(row, column);
                copyBoard.placeStone(row, column, color);
            }
        }
        return copyBoard;
    }

    //added getter for Junit test
    public char[][] getBoardRepresentation() {
        return boardRepresentation;
    }

    public char getStones(int row, int column) {
        return boardRepresentation[row][column];
    }

    public void setStones(int row, int column, char color) {
        boardRepresentation[row][column] = color;
    }


    @Override
    public String toString() {
        StringBuilder boardStringBuilder = new StringBuilder();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                boardStringBuilder.append(boardRepresentation[row][column]);
            }
        }

        return boardStringBuilder.toString();
    }
}




