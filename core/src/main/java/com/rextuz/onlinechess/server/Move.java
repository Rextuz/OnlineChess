package com.rextuz.onlinechess.server;

import java.io.Serializable;

public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int x1, y1, x2, y2;
	private String piece;
	private boolean isPromotion;

	public Move(String name, int x1, int y1, int x2, int y2) {
		this.name = name;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		isPromotion = false;
	}

	public Move(String name, int x1, int y1, String piece) {
		this.name = name;
		this.x1 = x1;
		this.y1 = y1;
		this.piece = piece;
		isPromotion = true;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public String getName() {
		return name;
	}

	public String getPiece() {
		return piece;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}
}