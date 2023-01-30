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
    private Player currentPlayer;
    private int turn;
    private List<String> movesHistory;
    private Game game;

    public GameHandler() {
        movesHistory = new ArrayList<>();
    }

    public void startNewGame(Player player1, Player player2, Server server) {
        this.player1 = player1;
        this.player2 = player2;
        game = new Game(player1,player2);
        currentPlayer = player1;
        // Send message to players to let them know the game has started
        player1.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
        player2.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
        // Notify the player who can make the first move
        player1.sendMessageToClient(YOURTURN + SEPARATOR + player1.getUsername());
    }




        public synchronized void processInput(String input) {
            System.out.println("Input in processInput()" + input);
            String[] parts = input.split(SEPARATOR);
            String command = parts[0];
            switch (command) {
            case MOVE:
//                /*
//                * MOVE (client)
//                   Sent by the client to indicate which row(s) or column(s) the player wants to push.
//                   * Only allowed when it is the player's turn.
//                   *Syntax: MOVE~<ROW>~<COL> */
//                // Get move information
//                int row = Integer.parseInt(parts[1]);
//                int col = Integer.parseInt(parts[2]);
////                String player = parts[3];
////
////                if (turn == 1 && !player.equals(player1.getUsername()) || turn == 2 && !player.equals(player2.getUsername())) {
////                    // Not player's turn
////                    player1.sendMessageToClient(INVALIDMOVE + SEPARATOR + player + SEPARATOR + "Not player's turn");
////                    player2.sendMessageToClient(INVALIDMOVE + SEPARATOR + player + SEPARATOR + "Not player's turn");
////                    break;
////                }
//
////                if (!game.isValidMove(x, y)) {
////                    player1.sendMessageToClient(INVALIDMOVE + SEPARATOR + player + SEPARATOR + "Invalid move");
////                    player2.sendMessageToClient(INVALIDMOVE + SEPARATOR + player + SEPARATOR + "Invalid move");
////                    // Notify both players that it's their turn again
////                    player1.sendMessageToClient(YOURTURN + SEPARATOR + player);
////                    player2.sendMessageToClient(YOURTURN + SEPARATOR + player);
////                    return;
////                }
//                // Update game state
////                game.doMove(row, col, BLACK);
//                /*MOVE (server)
//                    Sent by the server to indicate the next move that is played.
//                    This is sent to all players in the game, including the player who performed the move.
//                    Syntax: MOVE~<USERNAME>~<ROW>~<COL>
//
//                * */
//
//                // Notify both players of the move
//                player1.sendMessageToClient(MOVE + SEPARATOR + player1 + row + SEPARATOR + col);
//                player2.sendMessageToClient(MOVE + SEPARATOR + player1 + row + SEPARATOR + col);
//                // Update the current player
//
//                if (currentPlayer == player1) {
//                    currentPlayer = player2;
//                    player2.sendMessageToClient(YOURTURN + SEPARATOR + player2.getUsername());
//                } else {
//                    currentPlayer = player1;
//                    player1.sendMessageToClient(YOURTURN + SEPARATOR + player1.getUsername());
//                }
                player1.sendMessageToClient(MOVE + input + "recieved");
                player2.sendMessageToClient(MOVE + "recieved");
                break;
            case PASS:
                game.pass();
                if (game.getAmountPasses() >= 2) {
                    player1.sendMessageToClient("The game has ended as both players have passed consecutive turns");
                    player2.sendMessageToClient("The game has ended as both players have passed consecutive turns");
                    //TODO: gameover(), winner
                }else {
                        if (currentPlayer == player1) {
                            currentPlayer = player2;
                            player2.sendMessageToClient(YOURTURN + SEPARATOR + player2.getUsername());
                        } else {
                            currentPlayer = player1;
                            player1.sendMessageToClient(YOURTURN + SEPARATOR + player1.getUsername());
                        }
                    break;
                }
                break;
            case QUIT:
                // player quits the game
                if (currentPlayer == player1) {
                    player1.sendMessageToClient("You have quit the game, player2 wins");
                    player2.sendMessageToClient(player1.getUsername() +" quit the game, you win");
                } else {
                    player2.sendMessageToClient("You have quit the game, player1 wins");
                    player1.sendMessageToClient(player2.getUsername() +" quit the game, you win");
                }
                break;
            default:
                player1.sendMessageToClient(ERROR);
                player2.sendMessageToClient(ERROR);
                break;
        }
    }
}




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





