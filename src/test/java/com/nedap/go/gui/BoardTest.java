package com.nedap.go.gui;

import com.nedap.go.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.nedap.go.board.Board.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitializeBoard() {
        board.initializeBoard();
        for (int row = 0; row < board.BOARD_SIZE; row++) {
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                assertEquals(EMPTY, board.getBoard()[row][column]);
            }
        }
    }


}