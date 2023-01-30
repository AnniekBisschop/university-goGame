package com.nedap.go.client;

import com.nedap.go.server.ClientHandler;
import com.nedap.go.server.GameHandler;

import java.io.IOException;

public class Player {

    private String username;

    private ClientHandler clientHandler;

    private GameHandler gameHandler;

    private char color;

    /**
     * This constructor creates a new player
     * @param username (String) is the name of the player
     * */
    public Player(String username, ClientHandler clientHandler) {
        this.username = username;
        this.clientHandler = clientHandler;
    }

    /**
     * Returns the name of the player.
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public synchronized void sendMessageToClient(String message) {
        try {
            clientHandler.sendMessageFromClientHandler(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public synchronized void processInput(String input) {
        gameHandler.processInput(input);
    }

    //determine move for computer
}
