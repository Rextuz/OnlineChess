package com.rextuz.chess.debug;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.rextuz.chess.server.ServerSend;

public class Client {

	public static void main(String[] args) {
		String serverIP = "rextuz-pc";
		int PORT = 4242;
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, PORT);
			ServerSend stub = (ServerSend) registry.lookup("OnlineChess");
			System.out.println(stub.login("Rextuz"));
			System.out.println(stub.login("Max"));
			System.out.println(stub.login("Rextuz"));
			for (String s : stub.find("Rextuz"))
				System.out.println(s);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
