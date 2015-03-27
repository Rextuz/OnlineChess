package com.rextuz.chess.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;

public class Bishop extends Piece {

	public Bishop(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("bishop_" + color + ".png");
	}
}
