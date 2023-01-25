package com.nedap.go.client;

import com.nedap.go.server.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static com.nedap.go.Protocol.*;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private BufferedReader inputFromServer;
    private PrintWriter out;
    private boolean running;

    public Client(String serverIp, int port) {
        try {
           socket = new Socket(serverIp, port);
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            running = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
           } catch (IOException e) {
          e.printStackTrace();
       }
    }

    public void run() {
        System.out.println("Please enter your command:");
        while (running) {
            try {
                String message = in.readLine();
                if(message == null) continue;
                String[] parts = message.split(SEPARATOR);
                String command = parts[0];
                switch (command) {
                    case HELLO:
                        sendMessage(HELLO);
                        break;
                    case USERNAME:
                        username(parts[1]);
                        break;
                    case QUEUE:
                        sendMessage(QUEUE);
                        break;
                    case MOVE:
                        move(parts[1], parts[2]);
                        break;
                    case QUIT:
                        sendMessage(QUIT);
                        break;
                    case PASS:
                        sendMessage(PASS);
                        break;
                    default:
                        break;
                }
                receiveMessageFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
//Do{
// … receiveMessage
//while(noMessage)

    public void receiveMessageFromServer(){
        try {
            System.out.println(inputFromServer.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void username(String name) {
        sendMessage(USERNAME + SEPARATOR + name);
    }

    //parse int??
    public void move(String row, String column) {
        sendMessage(MOVE + SEPARATOR + row + SEPARATOR + column);
    }

    public void close() {
        try {
            socket.close();
            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 900);
        Thread thread = new Thread(client);
        thread.start();
    }
}

