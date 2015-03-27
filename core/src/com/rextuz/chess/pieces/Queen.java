package com.rextuz.chess.pieces;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;
import com.rextuz.chess.anim.Avalible;

public class Queen extends Piece {

	public Queen(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("queen_" + color + ".png");
	}

	@Override
	public List<Avalible> moves() {
		return null;
	}
}
