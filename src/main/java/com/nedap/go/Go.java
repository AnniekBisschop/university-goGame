package com.nedap.go;

import com.nedap.go.gui.GoGuiIntegrator;

import static com.nedap.go.board.Board.WHITE;

/**
 * Example on how to use the GoGui
 *
 */
public class Go {

	public final GoGuiIntegrator gogui;

	public Go(int boardSize) {
		gogui = new GoGuiIntegrator(true, true, boardSize);
		gogui.startGUI();
		gogui.setBoardSize(boardSize);
	}

	public void placeStoneOnBoard(int column, int row, char color){
		if(color == WHITE){
			gogui.addStone(column, row,false);
		}else{
			gogui.addStone(column, row,true);
		}
	}

	public void gameBoard() {
	}

	public static void main(String[] args) {
		new Go(9).gameBoard();
	}
}
