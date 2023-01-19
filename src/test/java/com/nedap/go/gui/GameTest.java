package com.nedap.go.gui;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.nedap.go.board.Board.BLACK;
import static com.nedap.go.board.Board.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Board board;
    private Game game;
    private static Player playerBlack;
    private static Player playerWhite;

    @BeforeEach
    public void setUp() {
        board = new Board();
        game = new Game(playerBlack,playerWhite,board);
    }


    @Test
    public void testIsCaptured() {
        // test capturing a white stone
        board.setStones(3,4, WHITE);
        board.setStones(3,3, BLACK);
        board.setStones(3,5, BLACK);
        board.setStones(2,4, BLACK);
        board.setStones(4,4, BLACK);
        assertTrue(game.isCaptured(3, 4));


        // test capturing a black stone
        board.setStones(3,4, BLACK);
        board.setStones(3,3, WHITE);
        board.setStones(3,5, WHITE);
        board.setStones(2,4, WHITE);
        board.setStones(4,4, WHITE);
        game.isCaptured(3, 4);
        assertTrue(game.isCaptured(3, 4));

        // test capturing an empty stone
        board.getBoardRepresentation()[3][4] = Board.EMPTY;
        game.isCaptured(3, 4);
        assertEquals(Board.EMPTY, board.getBoardRepresentation()[3][4]);
    }



}
