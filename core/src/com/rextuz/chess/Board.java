package com.rextuz.chess;

public class Board {
	String[][] cells = new String[8][8];
	Piece[][] pieces = new Piece[8][8];
	String color;

	public Board(String color) {
		this.color = color;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				cells[i][j] = "none";
				pieces[i][j] = null;
			}
		pieces[0][0] = new Rook(0, 0, color);
		pieces[7][0] = new Rook(7, 0, color);
		pieces[1][0] = new Knight(1, 0, color);
		pieces[6][0] = new Knight(6, 0, color);
		pieces[2][0] = new Bishop(2, 0, color);
		pieces[5][0] = new Bishop(5, 0, color);
		pieces[3][0] = new Queen(3, 0, color);
		pieces[4][0] = new King(4, 0, color);
		for (int x = 0, y = 1; x < 8; x++)
			pieces[x][y] = new Pawn(x, y, color);
	}

	public boolean empty(int x, int y) {
		if (cells[x][y].equals("none"))
			return true;
		return false;
	}
}
