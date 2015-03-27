package com.rextuz.chess;

import java.util.ArrayList;

import com.rextuz.chess.pieces.Piece;

public class Pieces extends ArrayList<Piece> {
	private static final long serialVersionUID = 1L;

	public Piece get(int x, int y) {
		for (Piece p : this)
			if (p.getX() == x && p.getY() == y)
				return p;
		return null;
	}
	
	public boolean isNull(int x, int y) {
		return get(x, y) == null;
	}
	
}
