package com.nedap.go.server;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    //queuedPlayers is an instance variable of type List<String> that is used to store a list of players who have queued
    // to participate in a game.
    private List<Player> queuedPlayers;
    // games is an instance variable of type List<Game> that is used to store a list of games currently in progress.
    private List<Game> games;
    //in constructor??
    private Board board = new Board();


    public GameHandler() {
        queuedPlayers = new ArrayList<>();
        games = new ArrayList<>();
    }

    /**
     * The queuePlayer(String player) method is used to add or remove a player from the list of queued players.
     * The method first checks if the player is already in the list of queued players (queuedPlayers) using the contains(player) method.
     * If the player is already in the list, the method removes the player from the list using the remove(player) method.
     * This allows the player to dequeue themselves.
     * If the player is not in the list, the method adds the player to the list using the add(player) method.
     * This allows the player to queue themselves.
     * */

    public void queuePlayer(Player player) {
        if(queuedPlayers.contains(player)) {
            queuedPlayers.remove(player);
        } else {
            queuedPlayers.add(player);
        }
    }

    /**
 * The startNewGame() method is used to start a new game when there are at least two players in the queue.
 * It first checks if there are at least two players in the queue by checking the size of the
 * queuedPlayers list (queuedPlayers.size() >= 2). If there are at least two players in the queue, it will proceed to start a new game.
 * It does this by removing the first two players from the queuedPlayers list using the remove(0) method which removes
 * the element at the given index. It stores the removed players in the variables player1 and player2.
 * Then, it creates a new instance of the Game class passing player1 and player2 as parameters and adds the new game to the games list.
 * Finally, it calls the broadcastNewGame(player1, player2) method passing player1 and player2 as parameters,
 * this method can be used to send a command to all clients to start the new game with player1 and player2 as the players.
 * */
public void startNewGame() throws IOException {
    if(queuedPlayers.size() >= 2) {
        Player playerBlack = queuedPlayers.remove(0);
        Player playerWhite = queuedPlayers.remove(0);
        Game game = new Game(playerBlack, playerWhite, board);
        games.add(game);
        broadcastNewGame(playerBlack, playerWhite);
    }
}

 //TODO: HOW TO broadcastNewGame to every client in one game?

    private void broadcastNewGame(Player playerBlack, Player playerWhite) throws IOException {

        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendNewGameCommand(playerBlack, playerWhite);
        }
    }

    public List<Player> getQueuedPlayers() {
        return queuedPlayers;
    }

}


