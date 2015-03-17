package com.rextuz.chess.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerSend extends Remote {

	public boolean login(String name) throws RemoteException;

	public String search(String name) throws RemoteException;

	public List<String> find(String name) throws RemoteException;

	public String connect(String name) throws RemoteException;

	public boolean disconnect(String name) throws RemoteException;

}
