package com.rextuz.chess.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchServerInterface extends Remote {

    public void move(String name, int x, int y, int x1, int y1) throws RemoteException;

}
