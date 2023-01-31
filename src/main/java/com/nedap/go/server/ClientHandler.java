package com.nedap.go.server;


import com.nedap.go.Protocol;
import com.nedap.go.client.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.server.Server.existingUsers;

/**
 * A client handler is a class or component that handles the communication between a server and a single client.
 * Its purpose is to handle the input and output streams, parse and validate messages, and perform the appropriate actions based on the message received.
 * In a multi-client server application, each client will have its own client handler running on a separate thread.
 * The client handler is responsible for reading messages from the client, validating them, and sending appropriate responses.
 * It also listens for events, such as when a client disconnects or sends a specific command.
 * The client handler is also responsible for maintaining the state of the client, such as their username, game state,
 * and other relevant information. It can also handle other tasks such as validating client's commands, and updating the game
 * state according to the command.
 * In short, the client handler acts as a bridge between the client and the server,
 * handling all the communication, validation, and logic for a single client.
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;
    private String username;
    private Player player;
    private String playerInput;
    private char color;
    private GameHandler gameHandler;
    private boolean queueCommandReceived = false;


    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);
                String[] parts = inputLine.split(SEPARATOR);
                String command = parts[0];
                switch (command) {
                    case HELLO:
                        sendMessageFromClientHandler(WELCOME);
                        break;
                    case USERNAME:
                       username = handleUserName(inputLine);
                        break;
                    case QUEUE:
                        if (queueCommandReceived) {
                            server.removeFromQueue(player);
                        } else {
                            player = server.addToQueue(username, color, this);
                            queueCommandReceived = true;
                        }
                        break;
                    case MOVE:
                    case PASS:
                    case QUIT:
                        playerInput = inputLine;
                        player.processInput(playerInput);
                        break;
                    default:
                        out.println(ERROR);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageFromClientHandler(String message) throws IOException {
        out.println(message);
    }
    private String handleUserName(String inputLine) throws IOException {
        String username = inputLine;
        String[] splitUsername = username.split(SEPARATOR);
        while (isUsernameTaken(splitUsername[1])) {
            out.println(USERNAMETAKEN + SEPARATOR + "Please enter another USERNAME");
            splitUsername = in.readLine().split(SEPARATOR);
            System.out.println("Received message from client: " + inputLine);
        }
        existingUsers.add(splitUsername[1]);
        out.println(JOINED + SEPARATOR + splitUsername[1]);
       for (String user : existingUsers) {
            System.out.println("User on server: " + user);
        };
       return splitUsername[1];
    }


    // Check if the username is taken by checking a Hashset of existing users
    // Return true if the username is taken, false otherwise
    boolean isUsernameTaken(String username) {
        return existingUsers.contains(username);
    }

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Player getPlayer() {
        return player;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        existingUsers.remove(username);
    }

}

