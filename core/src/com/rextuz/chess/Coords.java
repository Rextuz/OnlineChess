package com.rextuz.chess;

public class Coords {
	int x, y;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void change(int new_x, int new_y) {
		this.x = new_x;
		this.y = new_y;
	}
}
