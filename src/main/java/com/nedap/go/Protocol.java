package com.nedap.go;

public class Protocol {

    public static final String SEPARATOR = "~";
    public static final String HELLO = "HELLO";
    public static final String WELCOME = "WELCOME";
    public static final String USERNAME = "USERNAME";
    public static final String USERNAMETAKEN = "USERNAMETAKEN";
    public static final String QUEUE = "QUEUE";
    public static final String NEWGAME = "NEWGAME";
    public static final String MOVE = "MOVE";
    public static final String INVALIDMOVE  = "INVALIDMOVE";
    public static final String YOURTURN = "YOURTURN";
    public static final String PASS = "PASS";
    public static final String QUIT = "QUIT";
    public static final String GAMEOVER = "GAMEOVER";
    public static final String ERROR = "ERROR";

    /**
     * Build a new protocol message which instructs the server that you want to say something
     * @param message The message you want to send
     * @return the protocol message
     */
    public static String printMessage(String message) {
        return HELLO + SEPARATOR + message;
    }

    /**
     * Build a new protocol message which instructs a client that another client said something
     * @param from The name of the client that sent the message
     * @param message The message that was received from the client
     * @return the protocol message
     */
    public static String forwardMessage(String from, String message) {
        return HELLO + SEPARATOR + from + SEPARATOR + message;
    }
}
