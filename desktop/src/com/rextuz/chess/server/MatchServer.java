package com.rextuz.chess.server;

import java.rmi.RemoteException;

import com.rextuz.chess.Board;

public class MatchServer implements MatchServerInterface {

	@Override
	public boolean move(String user, Board board, int x, int y, int x1, int y1)
			throws RemoteException {
		return false;
	}

}
