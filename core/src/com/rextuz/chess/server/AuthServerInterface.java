package com.rextuz.chess.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuthServerInterface extends Remote {

    boolean login(String name) throws RemoteException;

    String search(String name) throws RemoteException;

    List<String> find(String name) throws RemoteException;

    String connect(String name) throws RemoteException;

    boolean disconnect(String name) throws RemoteException;

    void remove(String name) throws RemoteException;

}
