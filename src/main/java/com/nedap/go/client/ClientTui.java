package com.nedap.go.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.Scanner;

import static com.nedap.go.Protocol.*;

/**
 * A TUI (Text User Interface) is typically designed to run in a single thread,
 * meaning that it can only handle one user input and display output at a time.
 * This means that if multiple users were to interact with the TUI at the same time,
 * the TUI would not be able to handle the multiple inputs and outputs simultaneously,
 * leading to potential race conditions, lost inputs, and other issues.
 * */
//public class ClientTui {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter host address");
//        try {
//            InetAddress address = InetAddress.getByName(scanner.nextLine());
//            System.out.println("Port?");
//            int port = scanner.nextInt();
//            Client client = new Client();
//            if (client.connect(address, port)) {
//                client.sendMessage(HELLO);
//                client.run();
//            }
//        } catch (UnknownHostException e) {
//            System.out.println("Host could not be found");
//        }
//    }
//}