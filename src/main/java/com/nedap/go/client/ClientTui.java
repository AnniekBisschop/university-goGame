package com.nedap.go.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * A TUI (Text User Interface) is typically designed to run in a single thread,
 * meaning that it can only handle one user input and display output at a time.
 * This means that if multiple users were to interact with the TUI at the same time,
 * the TUI would not be able to handle the multiple inputs and outputs simultaneously,
 * leading to potential race conditions, lost inputs, and other issues.
 */
public class ClientTui {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("First, enter the server address: ");
        try {
            InetAddress address = InetAddress.getByName(scanner.nextLine());
            System.out.println("Now, enter the port number to connect to the server: ");
            int port = scanner.nextInt();
            scanner.nextLine();
            Client client = new Client(address, port);
            Thread thread = new Thread(client);
            thread.start();
            System.out.println("Please enter your command:");
            System.out.println("When you want to play with a computer player, after the WELCOME message, type USERNAME~CP");

        } catch (UnknownHostException e) {
            System.out.println("Not a correct connection");
        }
    }
}


