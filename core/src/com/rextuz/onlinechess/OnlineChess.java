package com.rextuz.onlinechess;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.onlinechess.pieces.Available;
import com.rextuz.onlinechess.pieces.Bishop;
import com.rextuz.onlinechess.pieces.King;
import com.rextuz.onlinechess.pieces.Knight;
import com.rextuz.onlinechess.pieces.Pawn;
import com.rextuz.onlinechess.pieces.Piece;
import com.rextuz.onlinechess.pieces.Queen;
import com.rextuz.onlinechess.pieces.Rook;
import com.rextuz.onlinechess.server.Helper;
import com.rextuz.onlinechess.server.Move;

public class OnlineChess extends ApplicationAdapter {
	public static Board board;
	private Helper helper;

	public static SpriteBatch batch;
	private BitmapFont font;

	private String myColor;
	private String foeColor;
	private Pieces backup;
	private Piece pS;
	private Piece promotion;
	private String myName, foeName;
	private boolean myTurn;
	private Pawn toPromote;
	private Move promotionMove;

	private boolean check;
	private boolean checkmate;
	private boolean stalemate;

	public OnlineChess(String myColor, String myName, String foeName,
			Helper helper) {
		myTurn = myColor.equals("white");
		this.myColor = myColor;
		this.foeColor = myColor.equals("black") ? "white" : "black";
		this.myName = myName;
		this.foeName = foeName;
		this.helper = helper;
	}

	private void promote(Pawn p) {
		toPromote = p;
		backup = new Pieces(board.pieces);
		board.pieces.clear();
		board.pieces.add(new Bishop(2, 3, myColor, board));
		board.pieces.add(new Queen(3, 4, myColor, board));
		board.pieces.add(new Rook(3, 3, myColor, board));
		board.pieces.add(new Knight(4, 3, myColor, board));
		Gdx.input.setInputProcessor(new PromoteAdapter());
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	private void promotePt2() {
		board.pieces = new Pieces(backup);
		promotion.move(toPromote.getX(), toPromote.getY(), board);
		board.pieces.add(promotion);
		board.pieces.remove(toPromote);
		helper.move(new Move(foeName, toPromote.getX(), toPromote.getY(),
				promotion.getClass().getSimpleName()));
		Gdx.input.setInputProcessor(new GameAdapter());
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new GameAdapter());
		batch = new SpriteBatch();
		board = new Board(myColor);
		font = new BitmapFont();
		generateFlags();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Move move = null;
					while (move == null) {
						move = helper.getMove(myName);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					moveFoe(move);
					if (timeToPromote()) {
						promotionMove = null;
						while (promotionMove == null) {
							promotionMove = helper.getMove(myName);
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					myTurn = true;
				}
			}
		}).start();
	}

	private void postPromotion() {
		if (promotionMove != null) {
			String promoted = promotionMove.getPiece();
			Piece promotion = null;
			if (promoted.equals("Rook"))
				promotion = new Rook(promotionMove.getX1(),
						promotionMove.getY2(), foeColor, board);
			else if (promoted.equals("Queen"))
				promotion = new Queen(promotionMove.getX1(),
						promotionMove.getY2(), foeColor, board);
			else if (promoted.equals("Bishop"))
				promotion = new Bishop(promotionMove.getX1(),
						promotionMove.getY2(), foeColor, board);
			else if (promoted.equals("Knight"))
				promotion = new Knight(promotionMove.getX1(),
						promotionMove.getY2(), foeColor, board);
			board.pieces.remove(board.pieces.get(promotionMove.getX1(),
					promotionMove.getX2()));
			board.pieces.add(promotion);
			promotionMove = null;
		}
	}

	private boolean timeToPromote() {
		for (Piece p : board.pieces)
			if (p.getClass().getSimpleName().equals("Pawn"))
				if (p.getColor().equals(foeColor))
					if (p.getY() == 7 || p.getY() == 0)
						return true;
		return false;
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
		renderHUD();
		batch.end();

		postPromotion();
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

	private Pawn checkPromotion() {
		for (Piece p : board.pieces)
			if (p.getColor().equals(myColor))
				if (p.getClass().getSimpleName().equals("Pawn"))
					if ((p.getY() == 7) || (p.getY() == 0))
						return (Pawn) p;
		return null;
	}

	private void moveFoe(Move move) {
		board.getPiece(move.getX1(), 7 - move.getY1()).move(move.getX2(),
				7 - move.getY2(), board);
	}

	private class GameAdapter extends InputAdapter {
		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {
			y = Gdx.graphics.getHeight() - y;
			generateFlags();
			if (myTurn) {
				if (pS == null) {
					pS = board.getPieceByReal(x, y);
					if (pS != null) {
						if (pS.getColor().equals(myColor)) {
							if (checkCheckmate(board, myColor))
								System.out.println("Checkmate");
							board.moves = filterMoves(board, pS);
						} else
							pS = null;
					}
				} else {
					int x1 = pS.getX();
					int y1 = pS.getY();
					int x2 = board.getVirtX(x);
					int y2 = board.getVirtY(y);
					for (Available a : board.moves)
						if (a.getX() == x2 && a.getY() == y2) {
							pS.move(x2, y2, board);
							helper.move(new Move(foeName, x1, y1, x2, y2));
							Pawn p = checkPromotion();
							if (p != null)
								promote(p);
							myTurn = false;
						}
					pS = null;
					board.moves.clear();
				}
			}
			return true;
		}
	}

	private class PromoteAdapter extends InputAdapter {
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			screenY = Gdx.graphics.getHeight() - screenY;
			Piece selected = board.getPieceByReal(screenX, screenY);
			if (selected != null) {
				promotion = selected;
				promotePt2();
				myTurn = true;
			}
			return true;
		}
	}

	private void generateFlags() {
		checkmate = checkCheckmate(board, myColor);
		check = checkCheck(board, myColor);
		stalemate = checkStalemate(board, myColor);

	}

	public void renderHUD() {
		Color color = font.getColor();
		font.setColor(Color.RED);
		int x = 2;
		int y = Gdx.graphics.getHeight() - 30;
		if (checkmate)
			font.draw(batch, "Checkmate", x, y);
		else if (check)
			font.draw(batch, "Check", x, y);
		else if (stalemate)
			font.draw(batch, "Stalemate", x, y);
		if (myTurn) {
			font.setColor(Color.GREEN);
			font.draw(batch, "Your turn", x, y);
		} else
			font.draw(batch, "Opponent's turn", x, y);
		font.setColor(color);
	}

	public static boolean checkCheck(Board board, String color) {
		String foeColor = color.equals("black") ? "white" : "black";
		List<Available> moves = board.getEnemyMoves(foeColor);
		King king = board.getKing(color);
		for (Available a : moves)
			if (a.getX() == king.getX() && a.getY() == king.getY())
				return true;
		return false;
	}

	public static List<Available> filterMoves(Board board, Piece p) {
		List<Available> newList = new ArrayList<Available>();
		for (Available a : p.getMoves())
			if (!moveGrantsCheck(board, p, a))
				newList.add(a);
		return newList;
	}

	public static boolean moveGrantsCheck(Board board, Piece p, Available a) {
		boolean result = false;
		int oldX = p.getX(), oldY = p.getY();
		p.safeMove(a.getX(), a.getY(), board);
		if (checkCheck(board, p.getColor()))
			result = true;
		p.safeMove(oldX, oldY, board);
		return result;
	}

	public static boolean checkCheckmate(Board board, String color) {
		if (checkCheck(board, color)) {
			for (Piece p : board.pieces)
				if (p.getColor().equals(color))
					if (filterMoves(board, p).isEmpty())
						return true;
		}
		return false;
	}

	public static boolean checkStalemate(Board board, String color) {
		if (checkCheck(board, color))
			if (!checkCheckmate(board, color))
				return true;
		return false;
	}

}
