package com.rextuz.onlinechess;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.onlinechess.pieces.Available;
import com.rextuz.onlinechess.pieces.Bishop;
import com.rextuz.onlinechess.pieces.King;
import com.rextuz.onlinechess.pieces.Knight;
import com.rextuz.onlinechess.pieces.Pawn;
import com.rextuz.onlinechess.pieces.Piece;
import com.rextuz.onlinechess.pieces.Queen;
import com.rextuz.onlinechess.pieces.Rook;

public class Board {
	public Pieces pieces = new Pieces();
	public List<Available> moves = new ArrayList<Available>();
	private String color;
	private Sprite sprite;
	private Texture texture;

	public Board(String color) {
		if (color.equals("white"))
			texture = new Texture("chess_board.png");
		else
			texture = new Texture("chess_board_black.png");
		sprite = new Sprite(texture);

		this.color = color;
		String foeColor = color.equals("white") ? "black" : "white";

		pieces.add(new Rook(0, 0, color, this));
		pieces.add(new Rook(7, 0, color, this));
		pieces.add(new Knight(1, 0, color, this));
		pieces.add(new Knight(6, 0, color, this));
		pieces.add(new Bishop(2, 0, color, this));
		pieces.add(new Bishop(5, 0, color, this));
		pieces.add(new Queen(3, 0, color, this));
		pieces.add(new King(4, 0, color, this));
		for (int x = 0, y = 1; x < 8; x++)
			pieces.add(new Pawn(x, y, color, this));

		pieces.add(new Rook(0, 7, foeColor, this));
		pieces.add(new Rook(7, 7, foeColor, this));
		pieces.add(new Knight(1, 7, foeColor, this));
		pieces.add(new Knight(6, 7, foeColor, this));
		pieces.add(new Bishop(2, 7, foeColor, this));
		pieces.add(new Bishop(5, 7, foeColor, this));
		pieces.add(new Queen(3, 7, foeColor, this));
		pieces.add(new King(4, 7, foeColor, this));
		for (int x = 0, y = 6; x < 8; x++)
			pieces.add(new Pawn(x, y, foeColor, this));
	}

	public static String log(float x, float y) {
		String s = "(" + x + ", " + y + ")";
		System.out.println(s);
		return s;
	}

	public List<Available> getMoves(String color) {
		List<Available> moves = new ArrayList<Available>();
		for (Piece p : pieces)
			if (p.getColor().equals(color))
				for (Available a : p.getEnemyMoves())
					moves.add(a);
		return moves;
	}

	public King getKing(String color) {
		for (Piece p : pieces)
			if (p.getClass().getSimpleName().equals("King"))
				if (p.getColor().equals(color))
					return (King) p;
		return null;
	}

	public boolean cellEmpty(int x, int y) {
		if (pieces.isNull(x, y))
			return true;
		return false;
	}

	public Piece getPieceByReal(float x, float y) {
		for (Piece p : pieces)
			if (x > p.getRealX() && x < p.getRealX() + sprite.getWidth() / 8)
				if (y > p.getRealY()
						&& y < p.getRealY() + sprite.getHeight() / 8)
					return p;
		return null;
	}

	public Piece getPiece(int x, int y) {
		return pieces.get(x, y);
	}

	public float getX() {
		return sprite.getX();
	}

	public float getY() {
		return sprite.getY();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getSize() {
		return sprite.getWidth();
	}

	public void setSize(float size) {
		sprite.setSize(size, size);
	}

	public int getVirtX(float x) {
		x -= sprite.getX();
		int a = (int) sprite.getWidth() / 8;
		return (int) x / a;
	}

	public int getVirtY(float y) {
		y -= sprite.getY();
		int a = (int) sprite.getHeight() / 8;
		return (int) y / a;
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void setCenter(int x, int y) {
		sprite.setCenter(x, y);
	}

	public void dispose() {
		texture.dispose();
	}

	public List<Available> getEnemyMoves(String foeColor) {
		List<Available> moves = new ArrayList<Available>();
		for (Piece p : pieces)
			if (p.getColor().equals(foeColor))
				for (Available a : p.getEnemyMoves())
					moves.add(a);
		return moves;
	}

}
