package com.nedap.go.client;

public class Player {

    private String username;

    /**
     * This constructor creates a new player
     * @param username (String) is the name of the player
     * */
    public Player(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the player.
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }


}
