# Simple Two player Go game in Java

## Starting point for the Go game

* Usage of Java 19 is required
* Clone (with Git) or download this project to your PC
* Use Maven to build the project (or use your IDE)
* Run the application using the commandline or IDE
* You can use the packages in src/main/java/com.nedap.go directory to play the game
* Use the server package for the server and the client package for the client
* Start the Server in the server package so it can start listening for connections
* At the moment it listens at port 910 but you can change that if you wish
* Now you can start the ClientTui. Enter a server address and port first

## Setup game
* You can adjust the size of the board in the board class by adjusting the BOARD_SIZE. It is now setup for a 9*9


## Instructions and commands

* Follow the handshake.
* Client sends HELLO~text to the server
* Server responds with WELCOME~server by Anniek
* You can now choose your username by typing USERNAME~yourusernamehere
* If it is already taken you get the message USERNAMETAKEN. You can now choose a different username.
* If you like to play with a computerplayer you type USERNAME~CP
* When the server accepts the username, you will get the response JOINED~showsyourusername
* If you would like to start the game you type QUEQUE. When there are to players in the queue a new game will start
* Server responds NEWGAME~USERNAME1~USERNAME2
* The first player in the queue is player 1, plays with Black and starts with Black
* To make a move the player types MOVE~row~col
* Then players can take turns


## Rules
* Ko rules, and captured stones rules are handled. 
* The game ends when there are two consecutive pass moves.
* When a player types QUIT the other player wins automatically

## Score calculation


