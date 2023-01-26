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
            System.out.println("init");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);  // add this line
                if (inputLine.equals(HELLO)) {
                    System.out.println("Test2");
                    out.println(WELCOME);
                }
                username = server.handleUsername(in, out);
                System.out.println("Got username?:" + username);
                // Send the JOINED message to the client
                out.println(JOINED + SEPARATOR + username);
                if (inputLine.equals(QUEUE)) {
                    System.out.println("reached queue");
                    server.handleQueueCommand();
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

    public String getUsername() {
        return username;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        server.existingUsers.remove(username);
    }
}

