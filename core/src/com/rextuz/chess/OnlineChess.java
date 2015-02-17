package com.rextuz.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OnlineChess extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite sprite;
	BitmapFont font;
	int width;
	int height;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("chess_board.png");
		sprite = new Sprite(img);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getWidth();
		sprite.setSize(width, width);
		font = new BitmapFont();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		font.draw(batch, width + "", 100, 100);
		batch.end();

	}
}
