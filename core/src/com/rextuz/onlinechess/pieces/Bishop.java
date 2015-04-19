package com.rextuz.onlinechess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.rextuz.onlinechess.Board;
import com.rextuz.onlinechess.anim.Available;

public class Bishop extends Piece {

	public Bishop(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture(Gdx.files.internal("android/assets/bishop_" + color + ".png"));
	}

	@Override
	public List<Available> moves() {
		List<Available> list = new ArrayList<Available>();
		int xt, yt;

		xt = x + 1;
		yt = y + 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Available(xt, yt, board, 0));
			xt++;
			yt++;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Available(xt, yt, board, 1));

		xt = x - 1;
		yt = y - 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Available(xt, yt, board, 0));
			xt--;
			yt--;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Available(xt, yt, board, 1));

		xt = x + 1;
		yt = y - 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Available(xt, yt, board, 0));
			xt++;
			yt--;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Available(xt, yt, board, 1));

		xt = x - 1;
		yt = y + 1;
		while (valid(xt, yt) && board.cellEmpty(xt, yt)) {
			list.add(new Available(xt, yt, board, 0));
			xt--;
			yt++;
		}
		if (valid(xt, yt))
			if (!board.pieces.get(xt, yt).getColor().equals(color))
				list.add(new Available(xt, yt, board, 1));

		return list;
	}
}
