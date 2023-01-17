package com.nedap.go;
/**
 * The game class implements the rules of the Go game
 * */

public class Game {


    //players take turn, alternation of player
//    public void switchPlayer() {
//        if (currentPlayer == player1) {
//            currentPlayer = player2;
//        } else {
//            currentPlayer = player1;
//        }
//    }

    //Ko: A player cannot repeat a board position that has occurred previously in the game.

    //Illegal moves: A player cannot place a stone on a point that is already occupied or that would violate any of the other rules.

    //Pass: A player can choose to pass their turn if they do not want to make a move.

    //End of Game: The game ends when both players pass consecutively.
    //A stone or solidly connected group of stones of one color is captured and removed from the board
    // when all the intersections directly orthogonally adjacent to it are occupied by the opponent.

    //Scoring: The number of captured stones and territory must be accurately calculated at the end of the game.

    //checkBoard is full()


}
