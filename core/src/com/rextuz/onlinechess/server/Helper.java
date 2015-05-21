package com.rextuz.onlinechess.server;

import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Helper implements Serializable {
	private static final long serialVersionUID = 1L;
	boolean flag;
	private Socket s;
	private String host;
	private int port;

	public Helper(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean connect() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					s = new Socket(host, port);
					flag = true;
				} catch (Exception e) {
					flag = false;
				}
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return flag;
	}

	public boolean login(final String name) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				new Message("login " + name).send(s);
				Message message = Message.receiveMessage(s);
				return message.getBoolean();
			}
		});
		executor.shutdown();
		return future.get();
	}

	public boolean disconnect(String name) {
		new Message("disconnect " + name).send(s);
		Message message = Message.receiveMessage(s);
		return message.getBoolean();
	}

	public List<String> find() {
		new Message("find").send(s);
		Message message = Message.receiveMessage(s);
		return message.getList();
	}

	public String search(String myName) {
		new Message("search " + myName).send(s);
		Message message = Message.receiveMessage(s);
		return message.getArg(1);
	}

	public String connect(String myName) {
		new Message("connect " + myName).send(s);
		Message message = Message.receiveMessage(s);
		return message.getArg(1);
	}

	public void remove(String myName) {
		new Message("remove " + myName).send(s);
		Message.receiveMessage(s);
	}

	public void move(Move move) {
		String r = "move";
		new Message(r, move).send(s);
		Message.receiveMessage(s);
	}

	public Move getMove(String myName) {
		new Message("getMove " + myName).send(s);
		Message message = Message.receiveMessage(s);
		return message.getMove();
	}
}
