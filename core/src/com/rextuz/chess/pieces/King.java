package com.rextuz.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.rextuz.chess.Board;
import com.rextuz.chess.anim.Avalible;

public class King extends Piece {

	public King(int x, int y, String color, Board board) {
		super(x, y, color, board);
		texture = new Texture("king_" + color + ".png");
	}
	
	@Override
	public List<Avalible> moves() {
		List<Avalible> list = new ArrayList<Avalible>();
		
		List<int[]> moves = new ArrayList<int[]>();
		moves.add(new int[] {x + 1, y + 1});
		moves.add(new int[] {x, y + 1});
		moves.add(new int[] {x - 1, y + 1});
		moves.add(new int[] {x - 1, y});
		moves.add(new int[] {x - 1, y - 1});
		moves.add(new int[] {x, y - 1});
		moves.add(new int[] {x + 1, y - 1});
		moves.add(new int[] {x + 1, y});
		
		for (int[] a : moves) {
			int xt = a[0];
			int yt = a[1];
			if (valid(xt, yt) && board.cellEmpty(xt, yt))
				list.add(new Avalible(xt, yt, board, 0));
		}
		return list;
	}
}
