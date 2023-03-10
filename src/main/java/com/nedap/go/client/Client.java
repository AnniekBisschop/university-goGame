package com.nedap.go.client;

import com.nedap.go.Go;

import java.io.*;
import java.net.*;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.board.Board.BOARD_SIZE;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private BufferedReader inputFromServer;
    private PrintWriter outputFromClient;
    private boolean running;

    public Client(InetAddress serverIp, int port) {
        try {
           socket = new Socket(serverIp, port);
            in = new BufferedReader(new InputStreamReader(System.in));
            outputFromClient = new PrintWriter(socket.getOutputStream(), true);
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            running = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
           } catch (IOException e) {
          e.printStackTrace();
       }
    }


    @Override
    public void run() {
        MessageListener listener = new MessageListener();
        new Thread(listener).start();

        while (running) {
            try {
                String message = in.readLine();
                if(message == null) continue;

                if(message.equals(QUIT)){
                    sendMessage(message);
                    close();
                }else {
                    sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveMessageFromServer(){
        try {
            String message = inputFromServer.readLine();
            if (message == null) {
                running = false;
                close();
                return;
            }
            String[] parts = message.split(SEPARATOR);
            if (parts.length >= 2) {
                String command = parts[0];
                if (parts[1].equals("CP") || parts[1].equals("cp")) {
                    switch(command) {
                        case YOURTURN:
                        case INVALIDMOVE:
                            String computerMove = determineMove();
                            sendMessage(computerMove);
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("Disconnected from server");
           close();
        }
    }

    public String determineMove() {
        int row;
        int column;
        String move = "";

        do {
            row = (int) (Math.random() * BOARD_SIZE);
            if (row == 0) {
                row = 1;
            }
            column = (int) (Math.random() * BOARD_SIZE);
            if (column == 0) {
                column = 1;
            }
            move = MOVE + SEPARATOR + row + SEPARATOR + column;
        } while (row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE);

        return move;
    }

    public void sendMessage(String message) {
        outputFromClient.println(message);
    }
    public void sendUsernameToServer(String name) {
        sendMessage(USERNAME + SEPARATOR + name);
    }

    public void move(String row, String column) {
        sendMessage(MOVE + SEPARATOR + row + SEPARATOR + column);
    }

    public void close() {
        try {
            inputFromServer.close();
            outputFromClient.close();
            socket.close();
            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class MessageListener implements Runnable {
        @Override
        public void run() {
            while (running) {
                receiveMessageFromServer();
            }
        }
    }
}

