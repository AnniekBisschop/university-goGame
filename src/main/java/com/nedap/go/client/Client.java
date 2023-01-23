package com.nedap.go.client;

import com.nedap.go.server.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static com.nedap.go.Protocol.*;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running;

    public Client(String serverIp, int port) {
        try {
            socket = new Socket(serverIp, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            running = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your command:");
        while (running) {
            try {
                System.out.println("TEST");
                //TODO: Check this input!! DOES NOT WORK!!
                String message = in.readLine();
                System.out.println("TEST");
                System.out.println("Message form client: " + message);
                if(message == null) continue;
                String[] parts = message.split(SEPARATOR);
                String command = parts[0];
                switch (command) {
                    case HELLO:
                        System.out.println("TEST in SWitch HELLO");
                        sendMessage(HELLO);
                        break;
                    case USERNAME:
                        username(parts[1]);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println("**sendmessage**");
        out.println(message);
        System.out.println("after sendmessage");
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
        Client client = new Client("localhost", 878);
        Thread thread = new Thread(client);
        thread.start();
    }
}

