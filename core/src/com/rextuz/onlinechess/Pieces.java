package com.rextuz.onlinechess;

import com.rextuz.onlinechess.pieces.Piece;

import java.util.ArrayList;

public class Pieces extends ArrayList<Piece> {
    private static final long serialVersionUID = 1L;

    public Pieces() {
        super();
    }

    public Pieces(Pieces pieces) {
        for (Piece p : pieces)
            add(p);
    }

    public Piece get(int x, int y) {
        for (Piece p : this)
            if (p.getX() == x && p.getY() == y)
                return p;
        return null;
    }

    public boolean isNull(int x, int y) {
        return get(x, y) == null;
    }

}
