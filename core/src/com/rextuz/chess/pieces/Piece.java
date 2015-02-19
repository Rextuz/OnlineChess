package com.rextuz.chess.pieces;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;

public class Piece {
	int x, y;
	float size = 10;
	Texture texture;
	String color;

	public Piece(int x, int y, String color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public List<Coords> moves() {
		return null;
	}

	public void move(int new_x, int new_y) {
		this.x = new_x;
		this.y = new_y;
	}

	public void render(Sprite board) {

	}

	public void setSize(float size) {
		this.size = size;
	}

	public void dispose() {
		texture.dispose();
	}
}
