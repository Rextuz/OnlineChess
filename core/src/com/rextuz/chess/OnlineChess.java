package com.rextuz.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OnlineChess extends ApplicationAdapter {
	public static Board board = new Board("white");
	SpriteBatch batch;
	Texture img;
	Sprite sboard;
	BitmapFont font;
	int width;
	int height;

	@Override
	public void create() {
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getWidth();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sboard.draw(batch);
		font.draw(batch, sboard.getX() + " " + sboard.getY(), 100, 100);
		new Pawn(0, 0, "white").render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		img = new Texture("chess_board.png");
		sboard = new Sprite(img);
		if (width < height)
			sboard.setSize(width, width);
		else
			sboard.setSize(height, height);
		sboard.setCenter(width / 2, height / 2);
		font = new BitmapFont();
	}
}
