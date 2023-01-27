package com.nedap.go.client;

import com.nedap.go.server.ClientHandler;
import com.nedap.go.server.GameHandler;

import java.io.IOException;

public class Player {

    private String username;

    private ClientHandler clientHandler;

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

    public void sendMessageToClient(String message) {
        try {
            clientHandler.sendMessageFromClientHandler(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //determine move for computer
}
