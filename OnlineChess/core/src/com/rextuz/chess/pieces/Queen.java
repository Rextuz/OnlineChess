package com.rextuz.chess.pieces;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;
import com.rextuz.chess.OnlineChess;

public class Queen extends Piece {

	public Queen(int x, int y, String color) {
		super(x, y, color);
		texture = new Texture("queen_" + color + ".png");
	}

	@Override
	public List<Coords> moves() {
		return null;
	}

	@Override
	public void render(Sprite board) {
		Sprite sprite = new Sprite(texture);
		sprite.setSize(size, size);
		sprite.setX(board.getX() + size * x);
		sprite.setY(board.getY() + size * y);
		sprite.draw(OnlineChess.batch);
	}
}
