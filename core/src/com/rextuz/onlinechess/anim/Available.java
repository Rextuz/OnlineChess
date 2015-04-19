package com.rextuz.onlinechess.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.rextuz.onlinechess.Board;
import com.rextuz.onlinechess.pieces.Piece;

public class Available extends Piece {

    private boolean offencive;

    public Available(int x, int y, Board board, int a) {
        super(x, y, "available", board);
        this.x = x;
        this.y = y;
        this.size = board.getSize() / 8;
        texture = new Texture(Gdx.files.internal("android/assets/available.png"));
        if (a != 0) {
            texture = new Texture(Gdx.files.internal("android/assets/attack.png"));
            offencive = true;
        } else
            offencive = false;
    }

    public boolean isOffencive() {
        return offencive;
    }

    public int[] getCoords() {
        return new int[]{x, y};
    }
}
