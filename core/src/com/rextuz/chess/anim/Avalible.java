package com.rextuz.chess.anim;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;
import com.rextuz.chess.pieces.Piece;

public class Avalible extends Piece {

	public Avalible(int x, int y, Board board, int a) {
		super(x, y, "avalible", board);
		this.x = x;
		this.y = y;
		this.size = board.getSize()/8;
		texture = new Texture("avalible.png");
		if (a != 0)
			texture = new Texture("attack.png");
	}

	public int[] getCoords() {
		return new int[] { x, y };
	}
}
