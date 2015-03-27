package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Board;
import com.rextuz.chess.OnlineChess;
import com.rextuz.chess.anim.Avalible;

public class Piece {
	protected int x, y;
	protected float size;
	protected Texture texture;
	protected String color;
	protected Board board;
	protected Sprite sprite;

	public Piece(int x, int y, String color, Board board) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.board = board;
	}

	public List<Avalible> moves() {
		List<Avalible> list = new ArrayList<Avalible>();
		return list;
	}

	public void move(int new_x, int new_y, Board board) {
		board.pieces.remove(board.getPiece(new_x, new_y));
		this.x = new_x;
		this.y = new_y;
	}

	public void render(Board board) {
		sprite = new Sprite(texture);
		sprite.setSize(size, size);
		float rx = board.getX();
		float ry = board.getY();
		sprite.setX(rx + size * x);
		sprite.setY(ry + size * y);
		sprite.draw(OnlineChess.batch);
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

	public float getRealX() {
		return sprite.getX();
	}

	public float getRealY() {
		return sprite.getY();
	}

	protected boolean valid(int x, int y) {
		if (x > -1 && x < 8)
			if (y > -1 && y < 8)
				return true;
		return false;
	}
	
}
