package com.nedap.go.client;

import com.nedap.go.Game;

import static com.nedap.go.board.Board.BOARD_SIZE;

public class ComputerPlayer {
    public void determineMove() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
//                if (isEmptySpot(row, column)) {
//                    placeStone(row, column, Game.getCurrentColor());
//                    return;
//                }
            }
        }
    }

}
