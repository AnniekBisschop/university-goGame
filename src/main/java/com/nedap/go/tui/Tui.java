package com.nedap.go.tui;

import com.nedap.go.Game;
import com.nedap.go.board.Board;
import com.nedap.go.client.Player;

import static com.nedap.go.board.Board.BLACK;
import static com.nedap.go.board.Board.WHITE;

public class Tui {


    public void run() {

        Player playerBlack = new Player("playerBlack");
        Player playerWhite = new Player("playerWhite");
        Board board = new Board();
        Game game = new Game(playerBlack,playerWhite,board);
        game.doMove(3,4, WHITE);
        game.doMove(3,3, BLACK);
        game.doMove(3,5, BLACK);
        game.doMove(2,4, BLACK);
        game.doMove(4,4, BLACK);
        System.out.println(board);
        board.printBoard();

        board.printBoard();

    }

    public static void main(String[] args) {
        Tui tui = new Tui();
        tui.run();
    }
}
