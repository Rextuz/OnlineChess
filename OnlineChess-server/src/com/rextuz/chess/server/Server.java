package com.rextuz.chess.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerSend {
	private static final long serialVersionUID = 1L;
	private Users users;
	private Users searching;

	public Server() throws RemoteException {
		users = new Users();
		searching = new Users();
	}

	public static void main(String[] args) throws RemoteException {
		try {
			Server server = new Server();
			ServerSend stub = (ServerSend) UnicastRemoteObject.exportObject(
					server, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("OnlineChess", (Remote) stub);
			System.out.print("Server is ready");
		} catch (Exception e) {
			System.err.print(e);
		}
	}

	@Override
	public boolean login(String name) {
		if (users.add(name))
			return true;
		return false;
	}

	@Override
	public List<String> find(String name) {
		searching.add(name);
		return searching.toList();
	}

	@Override
	public boolean connect(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect(String name) {
		if (users.remove(name))
			return true;
		return false;
	}

}
