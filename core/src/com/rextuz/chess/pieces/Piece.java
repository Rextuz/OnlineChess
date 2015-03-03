package com.rextuz.chess.pieces;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;

public class Piece {
	public int x, y;
	public float size = 10;
	public Texture texture;
	private String color;

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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getSize() {
		return size;
	}

	public String getColor() {
		return color;
	}
}
