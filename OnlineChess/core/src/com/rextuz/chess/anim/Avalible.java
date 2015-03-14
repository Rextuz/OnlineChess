package com.rextuz.chess.anim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rextuz.chess.Coords;
import com.rextuz.chess.OnlineChess;

public class Avalible {
	
	private Texture texture;
	private int size = 10;
	private int x, y;
	
	public Avalible(int x, int y) {
		this.x = x;
		this.y = y;
		texture = new Texture("avalible.png");
	}

	public Avalible(Coords c) {
		this.x = c.getX();
		this.y = c.getY();
		texture = new Texture("avalible.png");
	}

	public void render(Sprite board) {
		Sprite sprite = new Sprite(texture);
		sprite.setSize(size, size);
		sprite.setX(board.getX() + size * x);
		sprite.setY(board.getY() + size * y);
		sprite.draw(OnlineChess.batch);
	}
	
	public void dispose() {
		texture.dispose();
	}
	
}
