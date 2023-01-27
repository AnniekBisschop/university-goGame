package com.nedap.go.server;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nedap.go.Protocol.NEWGAME;
import static com.nedap.go.Protocol.SEPARATOR;

public class GameHandler {
    private Player player1;
    private Player player2;
    private List<String> movesHistory;
    private Game game;

    public GameHandler() {
        movesHistory = new ArrayList<>();
    }

    public void startNewGame(Player player1, Player player2, Server server) {
        this.player1 = player1;
        this.player2 = player2;
        game = new Game(player1,player2);
        // Send message to players to let them know the game has started
        player1.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
        player2.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
    }

    public void processInput(String input) {
        player1.sendMessageToClient("test");
        player2.sendMessageToClient("test");
    }

//    public void validateMove(ClientHandler player, String move) {
//        // Validate move here
//        if(game.isValidMove(move)){
//            doMove(player, move);
//        }else{
//            player.sendMessageToClient("Invalid move. Please try again.");
//        }
//    }

//    private void doMove(ClientHandler player, int move) {
//        // Update game state based on move
//        game.doMove(move);
//        movesHistory.add(move);
//        // Send message to other player to let them know the move
//        if (player == player1) {
//            player2.sendMessageToClient("Player 1 made a move: " + move);
//        } else {
//            player1.sendMessageToClient("Player 2 made a move: " + move);
//        }
//    }
}




