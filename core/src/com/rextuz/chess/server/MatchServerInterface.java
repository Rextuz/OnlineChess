package com.rextuz.chess.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchServerInterface extends Remote {

    void move(Move move) throws RemoteException;

    Move getMove(String name) throws RemoteException;

}
