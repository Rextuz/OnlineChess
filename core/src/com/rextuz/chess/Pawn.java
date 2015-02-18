package com.rextuz.chess;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pawn extends Piece {

	public Pawn(int x, int y, String color) {
		super(x, y, color);
	}

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

	public void move(int new_x, int new_y) {
		this.x = new_x;
		this.y = new_y;
	}
	
	public void render(SpriteBatch batch) {
		Texture texture = new Texture("pawn.png");
		Sprite sprite = new Sprite(texture);
		int width = Gdx.graphics.getWidth()/8;
		sprite.setSize(width, width);
		sprite.draw(batch);
	}
}
