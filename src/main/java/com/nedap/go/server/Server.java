package com.nedap.go.server;

import com.nedap.go.client.Player;

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

    public void processInputFromClient(Player player, String input) {
        String inputForGame = input;
        GameHandler gameHandler = gameHandlers.get(player);
        gameHandler.processInput(inputForGame);
    }
    public Player addToQueue(String username, ClientHandler clientHandler) {
        Player playerToAdd = new Player(username, clientHandler);
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
            GameHandler newGameHandler = new GameHandler();
            newGameHandler.startNewGame(player1, player2, this);
            numGamesStarted++;
            gameHandlers.put(player1, newGameHandler);
            gameHandlers.put(player2, newGameHandler);
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