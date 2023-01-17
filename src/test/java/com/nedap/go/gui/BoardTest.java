package com.nedap.go.gui;

import com.nedap.go.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.nedap.go.board.Board.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitializeBoard() {
        board.initializeBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                assertEquals(EMPTY, board.getBoard()[row][column]);
            }
        }
    }
    @Test
    public void testIsEmptySpot() {
        // test an empty spot
        Board.getBoard()[3][4] = Board.EMPTY;
        assertTrue(Board.isEmptySpot(3, 4));

        // test a non-empty spot for black
        Board.getBoard()[3][4] = Board.BLACK;
        assertFalse(Board.isEmptySpot(3, 4));

        // test a non-empty spot for white
        Board.getBoard()[2][8] = Board.WHITE;
        assertFalse(Board.isEmptySpot(2, 8));
    }
    @Test
    public void testIsBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                getBoard()[row][column] = EMPTY;
            }
        }
        assertFalse(board.isBoardFull(getBoard()));

        //sets every spot to black -> board is full
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                getBoard()[row][column] = BLACK;
            }
        }
        assertTrue(board.isBoardFull(getBoard()));
    }

}