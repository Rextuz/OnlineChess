package com.rextuz.chess;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.chess.anim.Avalible;
import com.rextuz.chess.pieces.Piece;

public class OnlineChess extends ApplicationAdapter {
	public static Board board;
	public static SpriteBatch batch;
	private Texture img;
	private Sprite sboard;
	private BitmapFont font;
	private String my_color;
	private String myName;
	private String foe;

	public OnlineChess(String my_color, String myName, String foe) {
		this.my_color = my_color;
		this.myName = myName;
		this.foe = foe;
	}

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchDown(int x, int y, int pointer, int button) {
				Piece piece = board.getPiece(x, y);
				if (piece != null) {
					if (piece.getColor().equals(my_color)) {
						List<Coords> moves = piece.moves();
						List<Avalible> av = new ArrayList<Avalible>();
						if (moves != null)
							for (Coords c : moves)
								av.add(new Avalible(c));
						else
							System.err.println(piece.x + " " + piece.y
									+ " has null moves");
						board.moves = av;
					}
				}
				return true;
			}

			public boolean touchUp(int x, int y, int pointer, int button) {
				// your touch up code here
				return true; // return true to indicate the event was handled
			}
		});
		batch = new SpriteBatch();
		img = new Texture("chess_board.png");
		sboard = new Sprite(img);
		font = new BitmapFont();
		board = new Board(my_color);
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
		for (Avalible a : board.moves)
			a.render(sboard);
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
		board.setX(sboard.getX());
		board.setY(sboard.getY());
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
