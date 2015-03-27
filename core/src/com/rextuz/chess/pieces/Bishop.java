package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;
import com.rextuz.chess.anim.Avalible;

public class Bishop extends Piece {

	public Bishop(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("bishop_" + color + ".png");
	}

	@Override
	public List<Avalible> moves() {
		List<Avalible> list = new ArrayList<Avalible>();
		int xt, yt;

		xt = x + 1;
		yt = y + 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Avalible(xt, yt, board, 0));
			xt++;
			yt++;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Avalible(xt, yt, board, 1));

		xt = x - 1;
		yt = y - 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Avalible(xt, yt, board, 0));
			xt--;
			yt--;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Avalible(xt, yt, board, 1));

		xt = x + 1;
		yt = y - 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Avalible(xt, yt, board, 0));
			xt++;
			yt--;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Avalible(xt, yt, board, 1));

		xt = x - 1;
		yt = y + 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Avalible(xt, yt, board, 0));
			xt--;
			yt++;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Avalible(xt, yt, board, 1));

		return list;
	}
}
