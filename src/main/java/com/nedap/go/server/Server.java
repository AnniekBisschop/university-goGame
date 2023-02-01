package com.nedap.go.server;

import com.nedap.go.client.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.board.Board.BLACK;
import static com.nedap.go.board.Board.WHITE;
import static java.lang.System.out;

public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    // Keep track of the active GameHandlers.
    private List<GameHandler> activeGameHandlers;
    // queue to keep track of waiting players (collection framework)
    private Queue<Player> waitingPlayers;
    // Keep track of all connected clients, joined and not joined the queue
    static HashSet<String> existingUsers = new HashSet<>();
    // variable to keep track of number of games started
    private int numGamesStarted;
    private HashMap<Player, GameHandler> gameHandlers;

    public Server(int port) {
        this.port = port;
        clientHandlers = new ArrayList<>();
        waitingPlayers = new LinkedList<>();
        gameHandlers = new HashMap<>();
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


    public Player addToQueue(String username, char color, ClientHandler clientHandler) {
        Player playerToAdd = new Player(username, color, clientHandler);
        waitingPlayers.offer(playerToAdd); // add to waiting players queue
        System.out.println(clientHandler.getUsername() + " JOINED THE QUEUE");
        startNewGameIfPossible();
        return playerToAdd;
    }

    public void removeFromQueue(Player player) {
        if (waitingPlayers.contains(player)) {
            waitingPlayers.remove(player);
            out.println(player.getUsername() +"REMOVED FROM QUEUE");
        } else {
            out.println(player + " not found in the queue");
        }
    }

    private void startNewGameIfPossible() {
        if (waitingPlayers.size() >= 2) {
            Player player1 = waitingPlayers.poll();
            Player player2 = waitingPlayers.poll();
            // Assign player1 color
            player1.setColor(BLACK);
            // Assign player2 color
            player2.setColor(WHITE);

            GameHandler newGameHandler = new GameHandler();
            newGameHandler.startNewGame(player1, player2, this);
            player1.setGameHandler(newGameHandler);
            player2.setGameHandler(newGameHandler);
            numGamesStarted++;
            gameHandlers.put(player1, newGameHandler);
            gameHandlers.put(player2, newGameHandler);
        }
    }
    public int getNumGamesStarted() {
        return numGamesStarted;
    }
//
//    public int getPort() {
//            return serverSocket.getLocalPort();
//    }
//
//    public void stop(){
//        try {
//            serverSocket.close();
//        } catch (IOException e) {
//            System.out.println("Server is already closed or not even opened at all.");
//            throw new RuntimeException(e);
//        }
//    }
    public static void main(String[] args) {
        Server server = new Server(910);
        Thread thread = new Thread(server);
        thread.start();

    }
}