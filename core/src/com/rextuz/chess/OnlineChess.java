package com.rextuz.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rextuz.chess.anim.Available;
import com.rextuz.chess.pieces.Piece;
import com.rextuz.chess.server.MatchServerInterface;
import com.rextuz.chess.server.Move;

import java.rmi.RemoteException;
import java.util.List;

public class OnlineChess extends ApplicationAdapter {
    public static Board board;
    public static SpriteBatch batch;
    private MatchServerInterface stub;
    private String my_color;
    private Piece pS;
    private String myName, foeName;
    private boolean myTurn;

    public OnlineChess(String my_color, String myName, String foeName, MatchServerInterface stub) {
        myTurn = my_color.equals("white");
        this.my_color = my_color;
        this.myName = myName;
        this.foeName = foeName;
        this.stub = stub;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                y = Gdx.graphics.getHeight() - y;
                Board.log(board.getVirtX(x), board.getVirtY(y));
                if (myTurn) {
                    if (pS == null) {
                        pS = board.getPieceByReal(x, y);
                        if (pS != null) {
                            if (pS.getColor().equals(my_color)) {
                                List<Available> moves = pS.moves();
                                if (moves.isEmpty())
                                    pS = null;
                                board.moves = moves;
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
                                try {
                                    stub.move(new Move(foeName, x1, y1, x2, y2));
                                    myTurn = false;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        pS = null;
                        board.moves.clear();
                    }
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        moveFoe(stub.getMove(myName));
                        myTurn = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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

    private void moveFoe(Move move) {
        board.getPiece(move.getX1(), 7 - move.getY1()).move(move.getX2(), 7 - move.getY2(), board);
    }

    public void move(Move move) throws RemoteException {
        board.getPiece(move.getX1(), move.getY1()).move(move.getX2(), move.getY2(), board);
    }
}
