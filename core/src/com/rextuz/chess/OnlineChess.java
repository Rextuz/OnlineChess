package com.rextuz.chess;

import java.rmi.RemoteException;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.chess.anim.Available;
import com.rextuz.chess.pieces.Piece;
import com.rextuz.chess.server.MatchServer;
import com.rextuz.chess.server.MatchServerInterface;

public class OnlineChess extends ApplicationAdapter implements ClientRemote {
	public static Board board;
	public static SpriteBatch batch;
	private String my_color;
	private Piece pS;
	private String myName, foeName;
	private MatchServerInterface stub = null;

	public OnlineChess(String my_color, String myName, String foeName) {
		this.my_color = my_color;
		this.myName = myName;
		this.foeName = foeName;
	}

	@Override
	public void create() {
		if (my_color.equals("white")) {
			try {
				MatchServer server = new MatchServer(myName, foeName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchDown(int x, int y, int pointer, int button) {
				y = Gdx.graphics.getHeight() - y;
				if (pS == null) {
					pS = board.getPieceByReal(x, y);
					if (pS != null) {
						Board.log(pS);
						if (pS.getColor().equals(my_color)) {
							List<Available> moves = pS.moves();
							if (moves.isEmpty())
								pS = null;
							board.moves = moves;
						} else
							pS = null;
					}
				} else {
					int vx = board.getVirtX(x);
					int vy = board.getVirtY(y);
					for (Available a : board.moves)
						if (a.getX() == vx && a.getY() == vy) {
							pS.move(vx, vy, board);
						}
					pS = null;
					board.moves.clear();
				}
				return true;
			}

			public boolean touchUp(int x, int y, int pointer, int button) {
				return true;
			}
		});
		batch = new SpriteBatch();
		try {
			board = new Board(my_color);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		board.render(batch);
		for (Piece p : board.pieces)
			p.render(board);
		for (Available a : board.moves)
			a.render(board);
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
		// board.setCenter(width / 2, height / 2);
		board.setSize(smaller);
		board.setCenter(width / 2, height / 2);
		for (Piece p : board.pieces)
			p.setSize(smaller / 8);
		for (Available a : board.moves)
			a.setSize(smaller / 8);
	}

	@Override
	public void dispose() {
		batch.dispose();
		board.dispose();
		for (Piece p : board.pieces)
			p.dispose();
		for (Available a : board.moves)
			a.dispose();
	}

	@Override
	public void move(int x, int y, int x1, int y1) throws RemoteException {
		board.getPiece(x, y).move(x1, y1, board);
	}
}
