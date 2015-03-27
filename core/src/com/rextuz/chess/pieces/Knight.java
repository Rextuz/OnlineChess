package com.rextuz.chess.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;

public class Knight extends Piece {

	public Knight(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("knight_" + color + ".png");
	}
}
