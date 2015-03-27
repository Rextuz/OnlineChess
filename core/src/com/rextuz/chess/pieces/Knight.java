package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;
import com.rextuz.chess.anim.Avalible;

public class Knight extends Piece {

	public Knight(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("knight_" + color + ".png");
	}

	@Override
	public List<Avalible> moves() {
		List<Avalible> list = new ArrayList<Avalible>();

		List<int[]> pMoves = new ArrayList<int[]>();
		pMoves.add(new int[] { x + 2, y + 1 });
		pMoves.add(new int[] { x + 1, y + 2 });
		pMoves.add(new int[] { x - 1, y + 2 });
		pMoves.add(new int[] { x - 2, y + 1 });
		pMoves.add(new int[] { x - 2, y - 1 });
		pMoves.add(new int[] { x - 1, y - 2 });
		pMoves.add(new int[] { x + 1, y - 2 });
		pMoves.add(new int[] { x + 2, y - 1 });

		for (int[] c : pMoves) {
			int xt = c[0];
			int yt = c[1];
			if (valid(xt, yt))
				if (board.cellEmpty(xt, yt))
					list.add(new Avalible(xt, yt, board, 0));
				else if (!board.pieces.get(xt, yt).getColor().equals(color))
					list.add(new Avalible(xt, yt, board, 1));
		}
		return list;
	}

}
