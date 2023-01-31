//package com.nedap.go.server;
//
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//public class ServerTui {
//    public static void main(String[] args) {
//        System.out.println("Enter port number");
//        Scanner scanner = new Scanner(System.in);
//        try {
//            int port = scanner.nextInt();
//            Server server = new Server(port);
//            server.run();
//            System.out.println("Server port is  " + server.getPort());
//            String input = "";
//            while (!input.equals("quit")) {
//                input = scanner.nextLine();
//            }
//            System.out.println("Connection with server is closed.");
//            server.stop();
//            scanner.close();
//        } catch (InputMismatchException e) {
//            System.out.println("No valid input");
//        }
//    }
//}
