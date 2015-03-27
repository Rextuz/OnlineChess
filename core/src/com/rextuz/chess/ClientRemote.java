package com.rextuz.chess;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemote extends Remote {

	public void move(int x, int y, int x1, int y1) throws RemoteException;
	
}
