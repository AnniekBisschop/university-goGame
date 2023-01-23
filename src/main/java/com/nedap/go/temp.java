package com.nedap.go;

import com.nedap.go.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class temp {

    //
//    private ClientSideConnection csc;
//    private int playerID;
//    private int otherPlayer;
//
//    //TODO: Where to setup the TUI/GUI
//    /*//switches turns
//    * Message.
//    * if(playerID == 1){
//    * You are player 1/black, you go first
//    * otherPlayer =2;
//    * able to make move
//    * }else{
//    * You are player 2/ white. Wait for your turn
//    * otherPlayer =1 ???
//    * not able to make a move
//    * }
//    * */
//    public void connectToServer(){
//        csc = new ClientSideConnection();
//    }
//    private class ClientSideConnection{
//        private Socket client;
//        private BufferedReader in;
//        private PrintWriter out;
//
//        //TODO: check if you have a write in the server. you need to have a read on the player
//        public ClientSideConnection(){
//            System.out.println("***** Client *****");
//            try{
//                client = new Socket("localhost", 888);
//                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                out = new PrintWriter(client.getOutputStream(),true);
//                playerID = Integer.parseInt(in.readLine());
//                System.out.println("Connected to server as Player " + playerID);
//            }catch(IOException e){
//                System.out.println("IO Exception from client side connection constructor");
//            }
//        }
//
//        public void sendMove(int row, int column, char color) throws IOException {
//            out.println(row);
//            out.println(column);
//            out.println(color);
//        }
//    }
//
//    public static void main(String[] args) {
//        Client client = new Client();
//        client.connectToServer();
//    }



    /*
    * Server class
    *  private ServerSocket serverSocket;
    private int numPlayers;

    private ServerSideConnection player1;
    private ServerSideConnection player2;


    public Server() {
        System.out.println("***** Server *****");
        numPlayers = 0;
        try {
            serverSocket = new ServerSocket(888);
        } catch (IOException e) {
            System.out.println("IOException from server constructor");
        }
    }

    /**
     * instructions for the server waiting for connections

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            //limit connections up to two players only
            while (numPlayers < 2) {
                //tell server to begin accepting connections
                Socket socket = serverSocket.accept();
                numPlayers++;
                System.out.println("Player " + numPlayers + " has connected");
                //create a new serverside connection
                //first player that connects is player 1
                //serverside connection can make use of input and output stream
                ServerSideConnection ssc = new ServerSideConnection(socket, numPlayers);
                //assigning correct runnable to correct field
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                //what is inside ServerSideConnection run() -> will run in new thread
                Thread thread = new Thread(ssc);
                thread.start();
            }
            System.out.println("We now have two players. This game is no longer accepting connections.");
        } catch (IOException e) {
            System.out.println("IOException from acceptConnections()");
        }

    }

    //create one thread for each player
    private class ServerSideConnection implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        //helps to tell if you are serverside connection for player 1 or player 2
        private int playerID;

        public ServerSideConnection(Socket s, int id) {
            this.socket = s;
            this.playerID = id;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                System.out.println("IOException from ServerSide Connection constructor");
            }
        }

        public void run() {
            //instruction you want to run on new thread
            //send which player the client is

            //try/catch??
            out.println(playerID);
            //allows server to send and receive more stuff
//                while(true){
//                    if(playerID == 1){
//                        //set a move?
//                    }
//                }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.acceptConnections();
    *
    *
    * */
}
