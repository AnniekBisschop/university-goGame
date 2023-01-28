package com.nedap.go.server;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.Protocol.QUIT;
import static com.nedap.go.board.Board.BLACK;

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
        while(true){
        String[] parts = input.split(SEPARATOR);
        String command = parts[0];
        switch (command) {
            case MOVE:
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                // Pass the move coordinates to the game object to make the move
//                game.makeMove(player1, x, y);
                // Send the move message to both players
                player1.sendMessageToClient("MOVE" + SEPARATOR + x + SEPARATOR + y);
                player2.sendMessageToClient("MOVE" + SEPARATOR + x + SEPARATOR + y);
                break;
            case PASS:
            case QUIT:
                player1.sendMessageToClient("You said " + input);
                player2.sendMessageToClient("You said " + input);
                break;
            default:
                break;
        }
    }}



//    public void validateMove(String move) {
//        // Validate move here
//        if(game.doMove(3,4,BLACK);){
//
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




