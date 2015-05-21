package com.rextuz.onlinechess.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthServerSocket extends Thread {
	private ServerSocket s;
	private Users users = new Users();
	private Users searching = new Users();
	private List<Move> moves = new ArrayList<Move>();

	public AuthServerSocket(int port) {
		try {
			s = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void move(Move move) {
		moves.add(move);
	}

	public Move getMove(String name) {
		for (Move move : moves)
			if (move.getName().equals(name)) {
				moves.remove(move);
				return move;
			}
		return null;
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			try {
				new ClientThread(s.accept(), this);
			} catch (Exception e) {
				running = false;
			}
		}
	}

	public void terminate() {
		try {
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean login(String name) {
		return users.add(name);
	}

	public List<String> find() {
		return searching.toList();
	}

	private boolean disconnect(String name) {
		return users.remove(name) || searching.remove(name);
	}

	public String connect(String name) {
		Random random = new Random();
		List<String> list = searching.toList();
		String foe = list.remove(random.nextInt(list.size()));
		searching.setFoe(foe, name);
		return foe;
	}

	public String search(String name) {
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

	public void remove(String name) {
		searching.remove(name);
	}

	private class ClientThread extends Thread {

		private Socket s;
		private AuthServerSocket server;

		public ClientThread(Socket s, AuthServerSocket server) {
			this.s = s;
			this.server = server;

			setDaemon(true);
			setPriority(NORM_PRIORITY);
			start();
		}

		public void run() {
			boolean running = true;
			while (running) {
				try {
					byte[] bytes = new byte[64 * 1024];
					s.getInputStream().read(bytes);
					Message m = new Message(new String(bytes));
					dealWithIt(m);
				} catch (Exception e) {
					running = false;
				}
			}
		}

		private void dealWithIt(Message m) {
			String command = m.getArg(0);
			if (command.equals("login"))
				new Message("login " + server.login(m.getArg(1))).send(s);
			else if (command.charAt(0) == 'f') {
				String result = "find ";
				List<String> list = server.find();
				result += list.size();
				for (String name : list)
					result += " " + name;
				new Message(result).send(s);
			} else if (command.equals("disconnect")) {
				new Message("disconnect " + server.disconnect(m.getArg(1))).send(s);
			} else if (command.equals("connect")) {
				new Message("connect " + server.connect(m.getArg(1))).send(s);
			} else if (command.equals("search")) {
				new Message("search " + server.search(m.getArg(1))).send(s);
			} else if (command.equals("remove")) {
				server.remove(m.getArg(1));
				new Message("remove").send(s);
			} else if (command.equals("move")) {
				server.move(m.getMove());
				new Message("move").send(s);
			} else if (command.equals("getMove")) {
				Move move = server.getMove(m.getArg(1));
				new Message("getMove", move).send(s);
			}
		}

	}
}
