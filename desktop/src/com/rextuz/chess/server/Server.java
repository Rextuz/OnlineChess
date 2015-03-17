package com.rextuz.chess.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

public class Server extends UnicastRemoteObject implements ServerSend {
	private static final long serialVersionUID = 1L;
	private Users users;
	private Users searching;
	private static int PORT;

	public Server() throws IOException {
		PORT = getPort();
		users = new Users();
		searching = new Users();
	}

	private int getPort() throws IOException {
		BufferedReader bufRead = new BufferedReader(new FileReader("hosts.cfg"));
		String host;
		int port = 0;
		String hostname;
		InetAddress addr;
		addr = InetAddress.getLocalHost();
		hostname = addr.getHostName();
		while ((host = bufRead.readLine()) != null) {
			String[] array1 = host.split(":");
			if (array1[0].equals(hostname))
				port = Integer.parseInt(array1[1]);
		}
		bufRead.close();
		return port;
	}

	public static void main(String[] args) throws RemoteException {
		try {
			Server server = new Server();
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind("OnlineChess", server);
			System.out.print("Server is ready");
		} catch (Exception e) {
			System.err.println(e);
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
		return searching.toList();
	}

	@Override
	public boolean disconnect(String name) {
		if (users.remove(name) || searching.remove(name))
			return true;
		return false;
	}

	@Override
	public String connect(String name) throws RemoteException {
		Random random = new Random();
		List<String> list = searching.toList();
		String foe = list.remove(random.nextInt(list.size()));
		searching.setFoe(foe, name);
		return foe;
	}

	@Override
	public String search(String name) throws RemoteException {
		searching.add(name);
		String foe = searching.getFoe(name);
		while (foe == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			foe = searching.getFoe(name);
		}
		return foe;
	}

}
