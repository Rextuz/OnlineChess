package com.rextuz.chess;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.chess.anim.Avalible;
import com.rextuz.chess.pieces.Bishop;
import com.rextuz.chess.pieces.King;
import com.rextuz.chess.pieces.Knight;
import com.rextuz.chess.pieces.Pawn;
import com.rextuz.chess.pieces.Piece;
import com.rextuz.chess.pieces.Queen;
import com.rextuz.chess.pieces.Rook;

public class Board {
	public Pieces pieces = new Pieces();
	public List<Avalible> moves = new ArrayList<Avalible>();
	private String color;
	private Sprite sprite;
	private Texture texture;

	public Board(String color) {
		texture = new Texture("chess_board.png");
		sprite = new Sprite(texture);

		this.color = color;

		pieces.add(new Rook(0, 0, "white", this));
		pieces.add(new Rook(7, 0, "white", this));
		pieces.add(new Knight(1, 0, "white", this));
		pieces.add(new Knight(6, 0, "white", this));
		pieces.add(new Bishop(2, 0, "white", this));
		pieces.add(new Bishop(5, 0, "white", this));
		pieces.add(new Queen(3, 0, "white", this));
		pieces.add(new King(4, 0, "white", this));
		for (int x = 0, y = 1; x < 8; x++)
			pieces.add(new Pawn(x, y, "white", this));

		pieces.add(new Rook(0, 7, "black", this));
		pieces.add(new Rook(7, 7, "black", this));
		pieces.add(new Knight(1, 7, "black", this));
		pieces.add(new Knight(6, 7, "black", this));
		pieces.add(new Bishop(2, 7, "black", this));
		pieces.add(new Bishop(5, 7, "black", this));
		pieces.add(new Queen(3, 7, "black", this));
		pieces.add(new King(4, 7, "black", this));
		for (int x = 0, y = 6; x < 8; x++)
			pieces.add(new Pawn(x, y, "black", this));
	}

	public void setSize(float size) {
		sprite.setSize(size, size);
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

	public static void log(Object s) {
		try {
			System.out.println(s.toString());
		} catch (Exception e) {
			System.out.println("null");
		}
	}

	public static String log(float x, float y) {
		String s = "(" + x + ", " + y + ")";
		System.out.println(s);
		return s;
	}

	public static void log(Piece p) {
		System.out.println("Name: " + p.getClass().getSimpleName());
		System.out.println("Color: " + p.getColor());
		System.out.println("Place: " + log(p.getX(), p.getY()));
		System.out.print("Moves: ");
		if (!p.moves().isEmpty())
			for (Avalible a : p.moves())
				System.out.print("(" + a.getX() + ", " + a.getY() + ") ");
		else
			System.out.print("none");
		System.out.println();
	}

	public int getVirtX(float x) {
		int a = (int) sprite.getWidth() / 8;
		return (int) x / a;
	}

	public int getVirtY(float y) {
		int a = (int) sprite.getHeight() / 8;
		return (int) y / a - 2;
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

}
