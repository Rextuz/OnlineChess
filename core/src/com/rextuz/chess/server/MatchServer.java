package com.rextuz.chess.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.rextuz.chess.Board;

public class MatchServer extends UnicastRemoteObject implements
		MatchServerInterface {
	private static final long serialVersionUID = 1L;

	public MatchServer() throws RemoteException {
	}

	boolean thinking;

	@Override
	public void move(String user, Board board, int x, int y, int x1, int y1)
			throws RemoteException {
		board.getPiece(x, y).move(x1, y1, board);
	}

	@Override
	public void wait(String user) throws RemoteException {
		thinking = true;
		while (thinking) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
