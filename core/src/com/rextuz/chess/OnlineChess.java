package com.rextuz.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OnlineChess extends ApplicationAdapter {
	public static Board board;
	public static SpriteBatch batch;
	private Texture img;
	private Sprite sboard;
	private BitmapFont font;
	private String color;

	public OnlineChess(String my_color) {
		color = my_color;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("chess_board.png");
		sboard = new Sprite(img);
		font = new BitmapFont();
		board = new Board(color);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sboard.draw(batch);
		font.draw(batch, " ", 100, 100);
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if (board.pieces[x][y] != null)
					board.pieces[x][y].render(sboard);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		batch.dispose();
		batch = new SpriteBatch();
		float smaller;
		if (width < height)
			smaller = width;
		else
			smaller = height;
		sboard.setSize(smaller, smaller);
		sboard.setCenter(width / 2, height / 2);
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if (board.pieces[x][y] != null)
					board.pieces[x][y].setSize(smaller / 8);
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if (board.pieces[x][y] != null)
					board.pieces[x][y].dispose();
	}
}
