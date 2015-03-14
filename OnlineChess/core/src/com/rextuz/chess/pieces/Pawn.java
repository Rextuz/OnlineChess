package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;
import com.rextuz.chess.OnlineChess;

public class Pawn extends Piece {

	public Pawn(int x, int y, String color) {
		super(new Coords(x, y), color);
		texture = new Texture("pawn_" + color + ".png");
	}

	@Override
	public List<Coords> moves() {
		List<Coords> list = new ArrayList<Coords>();
		if (y == 1)
			if (OnlineChess.board.empty(x, y + 2))
				list.add(new Coords(x, y + 2));
		if (y + 1 < 8) {
			if (OnlineChess.board.empty(x, y + 1))
				list.add(new Coords(x, y + 1));
			if (x - 1 > -1)
				list.add(new Coords(x - 1, y + 1));
			if (x + 1 < 8)
				list.add(new Coords(x + 1, y + 1));
		}
		return list;
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
