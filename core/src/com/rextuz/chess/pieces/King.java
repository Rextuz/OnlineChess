package com.rextuz.chess.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;

public class King extends Piece {

	public King(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("king_" + color + ".png");
	}
}
