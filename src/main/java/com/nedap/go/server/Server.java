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

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            out.println("Server started on port " + port);
            out.println("Waiting for a new connection");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                Thread thread = new Thread(clientHandler);
                thread.start();

                clientHandlers.add(clientHandler);
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

    public void addToQueue(ClientHandler clientHandler) {
        waitingPlayers.offer(clientHandler); // add to waiting players queue
        System.out.println(clientHandler.getUsername() + " JOINED THE QUEUE");
        startNewGameIfPossible();
    }

    public void removeFromQueue(ClientHandler clientHandler) {
        if (waitingPlayers.contains(clientHandler)) {
            waitingPlayers.remove(clientHandler);
            out.println(clientHandler.getUsername() +"REMOVED FROM QUEUE");
        } else {
            out.println(clientHandler + " not found in the queue");
        }
    }

    private boolean newGameStarted(){
        return true;
    }

    private void startNewGameIfPossible() {
        if (waitingPlayers.size() >= 2) {
            ClientHandler player1 = waitingPlayers.poll();
            ClientHandler player2 = waitingPlayers.poll();
            GameHandler newGameHandler = new GameHandler();
//            newGameHandler.startNewGame(player1, player2, this);
            player1.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
            player2.sendMessageToClient(NEWGAME + SEPARATOR + player1.getUsername() + SEPARATOR + player2.getUsername());
            activeGameHandlers.add(newGameHandler);
            numGamesStarted++;
        }
    }
    public int getNumGamesStarted() {
        return numGamesStarted;
    }

    public static void main(String[] args) {
        Server server = new Server(900);
        Thread thread = new Thread(server);
        thread.start();

    }
}