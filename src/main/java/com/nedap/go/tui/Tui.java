package com.nedap.go.tui;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Client;
import com.nedap.go.client.Player;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.nedap.go.Protocol.*;
import static com.nedap.go.board.Board.BLACK;
import static com.nedap.go.board.Board.WHITE;

/**
 * A TUI (Text User Interface) is typically designed to run in a single thread,
 * meaning that it can only handle one user input and display output at a time.
 * This means that if multiple users were to interact with the TUI at the same time,
 * the TUI would not be able to handle the multiple inputs and outputs simultaneously,
 * leading to potential race conditions, lost inputs, and other issues.
 * */
//public class Tui {
//    private Client client;
//    private Scanner scanner;
//
//    public Tui(Client client) {
//        this.client = client;
//        scanner = new Scanner(System.in);
//    }
//
//    public void run() {
//        System.out.println("Please enter your command:");
//        while (true) {
//            String message = scanner.nextLine();
//            String[] parts = message.split(SEPARATOR);
//            String command = parts[0];
//            switch (command) {
//                case HELLO:
//                    client.sendMessage(HELLO);
//                    break;
//                case USERNAME:
//                    client.username(parts[1]);
//                    break;
//                case QUEUE:
//                    client.sendMessage(QUEUE);
//                    break;
//                case MOVE:
//                    client.move(parts[1], parts[2]);
//                    break;
//                case QUIT:
//                    client.sendMessage(QUIT);
//                    break;
//                case PASS:
//                    client.sendMessage(PASS);
//                    break;
//                default:
//                    break;
//            }
//            client.receiveMessageFromServer();
//        }
//    }
//    public static void main(String[] args) {
//        Client client = new Client("localhost", 900);
//        Tui tui = new Tui(client);
//        tui.run();
//    }
//}

