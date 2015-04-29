package com.rextuz.onlinechess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.onlinechess.Board;
import com.rextuz.onlinechess.OnlineChess;

public class Pawn extends Piece {
	private static final long serialVersionUID = 1L;

	public Pawn(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("pawn_" + color + ".png");
	}

	@Override
	public List<Available> getMoves() {
		List<Available> list = new ArrayList<Available>();
		if (y == 1)
			if (OnlineChess.board.cellEmpty(x, y + 2))
				list.add(new Available(x, y + 2, board, 0));
		if (y + 1 < 8) {
			if (OnlineChess.board.cellEmpty(x, y + 1))
				list.add(new Available(x, y + 1, board, 0));
			if (x - 1 > -1)
				if (!OnlineChess.board.cellEmpty(x - 1, y + 1))
					if (!getColor(x - 1, y + 1).equals(color))
						list.add(new Available(x - 1, y + 1, board, 1));
			if (x + 1 < 8)
				if (!OnlineChess.board.cellEmpty(x + 1, y + 1))
					if (!getColor(x + 1, y + 1).equals(color))
						list.add(new Available(x + 1, y + 1, board, 1));
		}
		return list;
	}
/*
	@Override
	public List<Available> getEnemyMoves() {
		List<Available> list = new ArrayList<Available>();
		if (y == 6)
			if (OnlineChess.board.cellEmpty(x, y - 2))
				list.add(new Available(x, y - 2, board, 0));
		if (y > 0) {
			if (OnlineChess.board.cellEmpty(x, y - 1))
				list.add(new Available(x, y - 1, board, 0));
			if (x - 1 > -1)
				if (!OnlineChess.board.cellEmpty(x - 1, y - 1))
					if (!getColor(x - 1, y - 1).equals(color))
						list.add(new Available(x - 1, y - 1, board, 1));
			if (x + 1 < 8)
				if (!OnlineChess.board.cellEmpty(x + 1, y - 1))
					if (!getColor(x + 1, y - 1).equals(color))
						list.add(new Available(x + 1, y - 1, board, 1));
		}
		return list;
	}
*/
	private String getColor(int x, int y) {
		return OnlineChess.board.pieces.get(x, y).getColor();
	}

}
