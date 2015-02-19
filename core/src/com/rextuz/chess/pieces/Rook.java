package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;
import com.rextuz.chess.OnlineChess;

public class Rook extends Piece {

	public Rook(int x, int y, String color) {
		super(x, y, color);
		texture = new Texture("rook_" + color + ".png");
	}

	@Override
	public List<Coords> moves() {
		List<Coords> list = new ArrayList<Coords>();
		int xt, yt;
		xt = x + 1;
		yt = y;
		while (xt < 8 && OnlineChess.board.empty(xt, yt)) {
			list.add(new Coords(xt, yt));
			xt++;
		}
		xt = x - 1;
		while (xt > -1 && OnlineChess.board.empty(xt, yt)) {
			list.add(new Coords(xt, yt));
			xt--;
		}
		xt = x;
		yt = y + 1;
		while (yt < 8 && OnlineChess.board.empty(xt, yt)) {
			list.add(new Coords(xt, yt));
			yt++;
		}
		yt = y - 1;
		while (yt > -1 && OnlineChess.board.empty(xt, yt)) {
			list.add(new Coords(xt, yt));
			yt--;
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
