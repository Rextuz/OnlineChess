package com.rextuz.chess.server;

import java.io.Serializable;

public class Move implements Serializable {
    private String name;
    private int x1, y1, x2, y2;

    public Move(String name, int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public String getName() {
        return name;
    }
}