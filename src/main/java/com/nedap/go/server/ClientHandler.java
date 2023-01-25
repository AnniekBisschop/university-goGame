package com.nedap.go.server;


import com.nedap.go.client.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

import static com.nedap.go.Protocol.*;

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
    private Socket clientSocket;
    private GameHandler gameHandler;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;
    private static HashSet<String> existingUsers = new HashSet<>();
    private Player player;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.gameHandler = gameHandler;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Wait for the client's HELLO message
            String hello = in.readLine();
            if (!hello.equals(HELLO)) {
                out.println(ERROR);
                return;
            }

            // Send the WELCOME message to the client
            out.println(WELCOME);
            String username = handleUsername(in, out);

            // Send the JOINED message to the client
            out.println(JOINED+ SEPARATOR + username);
            String queue = in.readLine();
            if(!queue.equals(QUEUE)){
                out.println(ERROR);
                return;
            }else{
                handleQueueCommand();
            }

            //TODO: WHAT TO DO WHEN USER LEAVES/QUIT?
//            if (message.equals(QUIT)) {
//                existingUsers.remove(username);
//                out.println(username[1] + SEPARATOR + "left");
//                close();
//                return;
//            }

            // The initialization sequence has completed, now the client can play games

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleUsername(BufferedReader in, PrintWriter out) throws IOException {
        String[] username = in.readLine().split(SEPARATOR);
        while (isUsernameTaken(username[1])) {
            out.println(USERNAMETAKEN + SEPARATOR + "Please enter another USERNAME");
            username = in.readLine().split(SEPARATOR);
        }
        existingUsers.add(username[1]);
        for (String user : existingUsers) {
            System.out.println(JOINED + SEPARATOR + user);
        }
        return username[1];

    }

    // Check if the username is taken by checking a Hashset of existing users
    // Return true if the username is taken, false otherwise
    private boolean isUsernameTaken(String username) {
        return existingUsers.contains(username);
    }


    private boolean validateMove(String move) {
        // Validate the move by checking the game rules and current state
        // Return true if the move is valid, false otherwise
        return true;
    }
    public void handleQueueCommand() {
        // handle the queue command from client
        player = this.player; // get player object from client
        gameHandler.queuePlayer(player);
        System.out.println("reached handleQueueCommand");
        out.println("JOINED THE QUEUE" + "NOW IN QUEUE" + gameHandler.getQueuedPlayers());

        //server --> add client to queue
    }


    public void handleNewGameCommand(Player playerBlack, Player playerWhite) {
        // handle the new game command from server
    }

    public void sendNewGameCommand(Player playerBlack, Player playerWhite) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(NEWGAME +playerBlack.getUsername()+" "+playerWhite.getUsername());
        out.flush();
    }


    public void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ClientHandler{" +
                "gameHandler=" + gameHandler +
                '}';
    }
}
