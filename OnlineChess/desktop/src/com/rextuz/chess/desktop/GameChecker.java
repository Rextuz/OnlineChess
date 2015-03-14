package com.rextuz.chess.desktop;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import com.rextuz.chess.server.ServerSend;

public class GameChecker extends Thread {

	private String myName;
	private OnlineChessGUI gui;
	boolean works = true;

	public GameChecker(String myName, OnlineChessGUI gui) {
		this.myName = myName;
		this.gui = gui;
		start();
	}

	@Override
	public void run() {
		while (works) {
			try {
				Registry registry = LocateRegistry.getRegistry(gui.serverIP,
						gui.PORT);
				ServerSend stub = (ServerSend) registry.lookup("OnlineChess");
				List<String> l = stub.find(myName);
				java.util.Collections.sort(l);
				for (int i = 0; i < l.size(); i++)
					if (l.get(i).equals(myName))
						l.remove(i);
				gui.addGame(l);
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void kill() {
		works = false;
	}
}
