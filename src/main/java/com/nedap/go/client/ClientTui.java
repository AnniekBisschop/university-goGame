package com.nedap.go.client;

import java.util.Scanner;

/**
 * A TUI (Text User Interface) is typically designed to run in a single thread,
 * meaning that it can only handle one user input and display output at a time.
 * This means that if multiple users were to interact with the TUI at the same time,
 * the TUI would not be able to handle the multiple inputs and outputs simultaneously,
 * leading to potential race conditions, lost inputs, and other issues.
 * */
public class ClientTui {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client("localhost", 910);
        Thread thread = new Thread(client);
        thread.start();
        System.out.println("Please enter your command:");
//        client.run();

    }
}

