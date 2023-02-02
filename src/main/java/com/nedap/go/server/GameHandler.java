package com.nedap.go.server;

import com.nedap.go.Game;
import com.nedap.go.Go;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.Protocol.QUIT;
import static com.nedap.go.board.Board.BLACK;
import static com.nedap.go.board.Board.WHITE;

public class GameHandler {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int turn;
    private List<String> movesHistory;
    private Game game;
    private Go gogui;

    public GameHandler(int boardSize) {
        movesHistory = new ArrayList<>();
        gogui = new Go(boardSize);
    }

    public void startGame() {
        gogui.gameBoard();
    }

    public void startNewGame(Player player1, Player player2, Server server) {
        this.player1 = player1;
        this.player2 = player2;
        game = new Game(player1,player2);
        startGame();
        currentPlayer = game.getCurrentPlayer();
        // Send message to players to let them know the game has started
        player1.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
        player2.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
        // Notify the player who can make the first move
        player1.sendMessageToClient(YOURTURN + SEPARATOR + player1.getUsername());
    }

        public synchronized void processInput(String input) {
            String[] parts = input.split(SEPARATOR);
            String command = parts[0];
            switch (command) {
            case MOVE:

                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);

                // update the board and switch the current player
                game.doMove(row-1,col-1, game.getCurrentPlayer().getColor());
                gogui.placeStoneOnBoard(col-1,row-1, game.getCurrentPlayer().getColor());
                player1.sendMessageToClient(MOVE + SEPARATOR + currentPlayer.getUsername() + SEPARATOR + row + SEPARATOR + col);
                player2.sendMessageToClient(MOVE + SEPARATOR + currentPlayer.getUsername() + SEPARATOR + row + SEPARATOR + col);
                currentPlayer = game.getCurrentPlayer();
                game.getCurrentPlayer().sendMessageToClient(YOURTURN + SEPARATOR + currentPlayer.getUsername());

                break;
            case PASS:
                game.pass();
                if (game.getAmountPasses() >= 2) {
                    player1.sendMessageToClient(GAMEOVER);
                    player2.sendMessageToClient(GAMEOVER);
                    game.gameOver();
                }else {
                        if (currentPlayer == player1) {
                            currentPlayer = player2;
                            player2.sendMessageToClient(MOVE + SEPARATOR + player1.getUsername() + PASS);
                            player2.sendMessageToClient(YOURTURN + SEPARATOR + player2.getUsername());
                        } else {
                            currentPlayer = player1;
                            player1.sendMessageToClient(MOVE+ SEPARATOR+ player2.getUsername()+ PASS);
                            player1.sendMessageToClient(YOURTURN + SEPARATOR + player1.getUsername());
                        }
                    break;
                }
                break;
            case QUIT:
                // player quits the game
                if (currentPlayer == player1) {
                    player1.sendMessageToClient("You have quit the game, " + player2.getUsername() + " wins");
                    player2.sendMessageToClient(player1.getUsername() +" quit the game, you win");
                } else {
                    player2.sendMessageToClient("You have quit the game, " + player1.getUsername() + " wins");
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









