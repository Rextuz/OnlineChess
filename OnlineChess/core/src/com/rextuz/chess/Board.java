package com.rextuz.chess;

import com.rextuz.chess.pieces.Bishop;
import com.rextuz.chess.pieces.King;
import com.rextuz.chess.pieces.Knight;
import com.rextuz.chess.pieces.Pawn;
import com.rextuz.chess.pieces.Piece;
import com.rextuz.chess.pieces.Queen;
import com.rextuz.chess.pieces.Rook;

public class Board {
	Piece[][] pieces = new Piece[8][8];
	String color;
	int size;
	private float x, y;

	public void setX(float f) {
		this.x = f;
	}

	public void setY(float f) {
		this.y = f;
	}

	public Board(String color) {
		this.color = color;
		String foeColor;
		if (color.equals("white"))
			foeColor = "black";
		else
			foeColor = "white";
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				pieces[i][j] = null;
		// Allies
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
		// Foes
		pieces[0][7] = new Rook(0, 7, foeColor);
		pieces[7][7] = new Rook(7, 7, foeColor);
		pieces[1][7] = new Knight(1, 7, foeColor);
		pieces[6][7] = new Knight(6, 7, foeColor);
		pieces[2][7] = new Bishop(2, 7, foeColor);
		pieces[5][7] = new Bishop(5, 7, foeColor);
		pieces[3][7] = new Queen(3, 7, foeColor);
		pieces[4][7] = new King(4, 7, foeColor);
		for (int x = 0, y = 6; x < 8; x++)
			pieces[x][y] = new Pawn(x, y, foeColor);
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean empty(int x, int y) {
		if (pieces[x][y] == null)
			return true;
		return false;
	}

	public Piece getPiece(float x, float y) {
		x -= this.x;
		y -= this.y;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (pieces[i][j] != null)
					if (x > pieces[i][j].getX() * pieces[i][j].getSize()
							- pieces[i][j].getSize() / 2)
						if (x < pieces[i][j].getX() * pieces[i][j].getSize()
								+ pieces[i][j].getSize() / 2)
							if (y > pieces[i][j].getY()
									* pieces[i][j].getSize()
									- pieces[i][j].getSize() / 2)
								if (y < pieces[i][j].getY()
										* pieces[i][j].getSize()
										+ pieces[i][j].getSize() / 2) {
									System.out.println(pieces[i][j].getX());
									return pieces[i][j];
								}
		return null;
	}
}
