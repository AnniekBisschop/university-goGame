package com.nedap.go.server;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    private Player player1;
    private Player player2;
//    private ClientHandler player1;
//    private ClientHandler player2;
    private Board gameBoard;
    private List<String> movesHistory;
    private Game game;

//    public GameHandler() {
//        gameBoard = new Board();
//        movesHistory = new ArrayList<>();
//        game = new Game(player1,player2);
//    }
//
//    public void startNewGame(ClientHandler player1, ClientHandler player2, Server server) {
//        this.player1 = player1;
//        this.player2 = player2;
//        // Send message to players to let them know the game has started
//        player1.sendMessage("Game started. You are player 1.");
//        player2.sendMessage("Game started. You are player 2.");
//        game.start();
//    }
//
//    public void validateMove(ClientHandler player, String move) {
//        // Validate move here
//        if(game.isValidMove(move)){
//            doMove(player, move);
//        }else{
//            player.sendMessage("Invalid move. Please try again.");
//        }
//    }
//
//    private void doMove(ClientHandler player, int move) {
//        // Update game state based on move
//        game.doMove(move);
//        movesHistory.add(move);
//        // Send message to other player to let them know the move
//        if (player == player1) {
//            player2.sendMessage("Player 1 made a move: " + move);
//        } else {
//            player1.sendMessage("Player 2 made a move: " + move);
//        }
//    }
}





//public class GameHandler {
//    private ClientHandler player1;
//    private ClientHandler player2;
//    private Server server;
//   //  other game-related fields and methods
//
//    public void startNewGame(ClientHandler player1, ClientHandler player2, Server server) {
//        this.player1 = player1;
//        this.player2 = player2;
//        this.server = server;
//        Game game = new Game(player1, player2);
//        // Initialize the game state
//        // Set up the players' roles
//        // Send information to the players' client handlers to start the game
////        player1.sendMessage("You are player 1.");
////        player2.sendMessage("You are player 2.");
//    }
//    // other game-related methods
//}

//}
//* */