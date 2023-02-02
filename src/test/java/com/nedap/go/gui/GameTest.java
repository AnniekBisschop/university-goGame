package com.nedap.go.gui;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.nedap.go.board.Board.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Board board;
    private Game game;
    private Player playerBlack;
    private Player playerWhite;


    @BeforeEach
    public void setUp() {
        game = new Game(playerBlack, playerWhite);
        board = game.getBoard();
    }

    @Test
    public void testIsKo() {
        // Place a stone on the board
        game.doMove(0, 0, BLACK);

        // Test that the board history contains the current board state
        assertTrue(game.isKo(0, 0, BLACK));

    }

    @Test
    public void makeFieldEmpty() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 3, WHITE);
        board.setStones(3, 5, BLACK);
        board.setStones(2, 5, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        System.out.println(board.printBoard());

        game.makeFieldEmptyCapture(3, 4);
        game.makeFieldEmptyCapture(3, 5);
        assertEquals(EMPTY, board.getStones(3, 4));
        assertEquals(EMPTY, board.getStones(3, 5));
        System.out.println(board.printBoard());
    }

    @Test
    public void captureOneStone() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 3, WHITE);
        board.setStones(3, 5, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(4, 4, WHITE);
        System.out.println(board.printBoard());
        System.out.println("get stones: " + board.getStones(3, 4));
        assertTrue(game.isCaptured(3, 4, BLACK));
    }

    @Test
    public void testIsNotCapturedOneStone() {
        // test capturing an empty stone
        board.getBoardRepresentation()[3][4] = board.EMPTY;
        assertFalse(game.isCaptured(3, 4, EMPTY));
    }

    @Test
    public void testIsNotCapturedMissingStoneRight() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 3, WHITE);
        board.setStones(3, 5, BLACK);
        board.setStones(2, 5, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);

        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testIsNotCapturedTwoMissingStoneSTop() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testIsNotCapturedTwoMissingStoneSBottom() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(2, 5, WHITE);
        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testIsNotCapturedOneMissingStoneBottom() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(2, 5, WHITE);
        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));


    }
    @Test
    public void testIsNotCapturedOneMissingStoneLeft() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(2, 5, WHITE);
        board.setStones(2, 4, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);

        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testIsNotCapturedExtraStoneLeft() {
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, BLACK);
        board.setStones(2, 4, WHITE);
        board.setStones(2, 5, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        System.out.println(board.printBoard());

        assertFalse(game.isCaptured(3, 4, BLACK));
        assertFalse(game.isCaptured(3, 5, BLACK));
        assertFalse(game.isCaptured(3, 6, BLACK));
    }


    @Test
    public void testIsCapturedCornerOneStone() {

        board.setStones(8, 7, WHITE);
        board.setStones(7, 7, WHITE);
        board.setStones(7, 8, WHITE);
        board.setStones(8, 8, BLACK);
        System.out.println(board.printBoard());

        assertTrue(game.isCaptured(8, 8, BLACK));

    }

    @Test
    public void testIsCapturedCornerTwoStones() {
        board.setStones(6, 8, WHITE);
        board.setStones(8, 7, WHITE);
        board.setStones(7, 7, WHITE);
        board.setStones(7, 8, BLACK);
        board.setStones(8, 8, BLACK);
        System.out.println(board.printBoard());

        assertTrue(game.isCaptured(7, 8, BLACK));
        assertTrue(game.isCaptured(8, 8, BLACK));
    }

    @Test
    public void testIsCapturedBigGroup() {
        board.setStones(2, 4, BLACK);
        board.setStones(2, 5, BLACK);
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        board.setStones(2, 3, WHITE);
        board.setStones(1, 4, WHITE);
        board.setStones(1, 5, WHITE);
        board.setStones(2, 6, WHITE);
        System.out.println(board.printBoard());

        assertTrue(game.isCaptured(2, 4, BLACK));
        assertTrue(game.isCaptured(2, 5, BLACK));
        assertTrue(game.isCaptured(3, 4, BLACK));
        assertTrue(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testIsCapturedCornerGroup() {
        board.setStones(2, 4, BLACK);
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(2, 5, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        board.setStones(2, 3, WHITE);
        board.setStones(1, 4, WHITE);
        System.out.println(board.printBoard());

        assertTrue(game.isCaptured(2, 4, BLACK));
        assertTrue(game.isCaptured(3, 4, BLACK));
        assertTrue(game.isCaptured(3, 5, BLACK));
    }

    @Test
    public void testCapturedOpponentStonesCornerGroup() {
        board.setStones(2, 4, BLACK);
        board.setStones(3, 4, BLACK);
        board.setStones(3, 5, BLACK);
        board.setStones(2, 5, WHITE);
        board.setStones(4, 4, WHITE);
        board.setStones(4, 5, WHITE);
        board.setStones(3, 6, WHITE);
        board.setStones(3, 3, WHITE);
        board.setStones(2, 3, WHITE);
        board.setStones(1, 4, WHITE);
        System.out.println(board.printBoard());

        assertEquals(3, game.captureOpponentStones(WHITE));
    }
}
