package com.nedap.go.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static com.nedap.go.Protocol.*;
import static java.lang.System.out;

public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    // Keep track of the active GameHandlers.
    private List<GameHandler> activeGameHandlers;
    // queue to keep track of waiting players (collection framework)
    private Queue<ClientHandler> waitingPlayers;
    // Keep track of all connected clients, joined and not joined the queue
    static HashSet<String> existingUsers = new HashSet<>();
    // variable to keep track of number of games started
    private int numGamesStarted;

    public Server(int port) {
        this.port = port;
        clientHandlers = new ArrayList<>();
        waitingPlayers = new LinkedList<>();
    }

    //queue.

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            out.println("Server started on port " + port);
            out.println("Waiting for a new connection");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("New client connected: " + clientSocket);
                out.println("HERE?!");
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                Thread thread = new Thread(clientHandler);
                thread.start();

                clientHandlers.add(clientHandler);
                waitingPlayers.offer(clientHandler); // add to waiting players queue

                handleQueueCommand();
                startNewGameIfPossible();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                for (ClientHandler clientHandler : clientHandlers) {
                    clientHandler.close();
                }
                for (GameHandler gameHandler : activeGameHandlers) {
//                    gameHandler.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startNewGameIfPossible() {
        if (waitingPlayers.size() >= 2) {
            ClientHandler player1 = waitingPlayers.poll();
            ClientHandler player2 = waitingPlayers.poll();
            GameHandler newGameHandler = new GameHandler();
//         newGameHandler.startNewGame(player1, player2, this);
            activeGameHandlers.add(newGameHandler);
            numGamesStarted++;
        }
    }
    public int getNumGamesStarted() {
        return numGamesStarted;
    }

    String handleUsername(BufferedReader in, PrintWriter out) throws IOException {
        String username = in.readLine();
        System.out.println("HandleUsername after readline:" + username);
        String[] splitUsername = username.split(SEPARATOR);
        while (isUsernameTaken(splitUsername[1])) {
            out.println(USERNAMETAKEN + SEPARATOR + "Please enter another USERNAME");
            splitUsername = in.readLine().split(SEPARATOR);
        }
        existingUsers.add(splitUsername[1]);
        for (String user : existingUsers) {
            System.out.println(JOINED + SEPARATOR + user);
        }
        return splitUsername[1];

    }

        public void handleQueueCommand() {
        // handle the queue command from client
           System.out.println("JOINED THE QUEUE");
    }

    // Check if the username is taken by checking a Hashset of existing users
    // Return true if the username is taken, false otherwise
    private boolean isUsernameTaken(String username) {
        return existingUsers.contains(username);
    }


    public static void main(String[] args) {
        Server server = new Server(900);
        Thread thread = new Thread(server);
        thread.start();

    }
}