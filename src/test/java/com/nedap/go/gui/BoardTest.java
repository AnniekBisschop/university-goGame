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
                assertEquals(EMPTY, board.getBoardRepresentation()[row][column]);
            }
        }
    }
    @Test
    public void testIsEmptySpot() {
        // test an empty spot
        board.getBoardRepresentation()[3][4] = Board.EMPTY;
        assertTrue(board.isEmptySpot(3, 4));

        // test a non-empty spot for black
        board.getBoardRepresentation()[3][4] = Board.BLACK;
        assertFalse(board.isEmptySpot(3, 4));

        // test a non-empty spot for white
        board.getBoardRepresentation()[2][8] = Board.WHITE;
        assertFalse(board.isEmptySpot(2, 8));
    }
    @Test
    public void testIsBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
               board.getBoardRepresentation()[row][column] = EMPTY;
            }
        }
        assertFalse(board.isBoardFull(board.getBoardRepresentation()));

        //sets every spot to black -> board is full
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                board.getBoardRepresentation()[row][column] = BLACK;
            }
        }
        assertTrue(board.isBoardFull(board.getBoardRepresentation()));
    }

    @Test
    public void testCopyBoard() {
        // Place a stone on the board
        board.placeStone(0, 0, WHITE);
        assertEquals(WHITE,board.getStones(0,0));
        // Make a copy of the board
        Board copyBoard = board.copyBoard();
        assertEquals(WHITE,board.getStones(0,0));
        // Check that the copy board has the same stone at the same position as the original board
        assertEquals(board.getStones(0, 0), copyBoard.getStones(0, 0));
    }
}
