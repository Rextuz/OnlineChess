package com.rextuz.onlinechess.server;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String command = null;

	/*
	 * private String arg = null; private Piece piece = null; private
	 * List<String> list = null; private boolean flag = false; private Move move
	 * = null;
	 */

	public Message(String command) {
		command.trim();
		this.command = command;
	}

	public Message(String command, Move move) {
		command = command.trim();
		if (move != null) {
			command += " " + move.getName().trim();
			command += " " + Integer.toString(move.getX1());
			command += " " + Integer.toString(move.getY1());
			command += " " + Integer.toString(move.getX2());
			command += " " + Integer.toString(move.getY2());
		}
		this.command = command;
	}

	public void send(Socket s) {
		try {
			byte[] bytes = command.getBytes();
			s.getOutputStream().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getArg(int index) {
		String[] tokens = command.split(" ");
		return tokens[index];
	}

	public static Message receiveMessage(Socket s) {
		Message message = null;
		try {
			byte[] bytes = new byte[64 * 1024];
			s.getInputStream().read(bytes);
			message = new Message(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		int n;
		if (Integer.parseInt(command.charAt(5) + "") != 0)
			n = Integer.parseInt(getArg(1));
		else
			n = 0;
		for (int i = 2; i < n + 2; i++)
			list.add(getArg(i));
		return list;
	}

	public Move getMove() {
		Move m = null;
		try {
			if (!getArg(2).isEmpty())
				System.out.println(command);
			m = new Move(getArg(1), Integer.parseInt(getArg(2)),
					Integer.parseInt(getArg(3)), Integer.parseInt(getArg(4)),
					Integer.parseInt(getArg(5).trim()));
		} catch (Exception e) {
		}
		return m;
	}

	public boolean getBoolean() {
		if (getArg(1).charAt(0) == 't')
			return true;
		return false;
	}

	/*
	 * public Message(String command, String arg) { this.command = command;
	 * this.arg = arg; }
	 * 
	 * public Message(String command, String arg, Piece piece) { this.command =
	 * command; this.arg = arg; this.piece = piece; }
	 * 
	 * public Piece getPiece() { return piece; }
	 * 
	 * public Message(String command, List<String> list) { this.command =
	 * command; this.list = list; }
	 * 
	 * public Message(String command, boolean flag) { this.command = command;
	 * this.flag = flag; }
	 * 
	 * public Message(String command, Move move) { this.command = command;
	 * this.move = move; }
	 * 
	 * public static Message recover(byte[] bytes) { Message message = null; try
	 * { ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput
	 * in = new ObjectInputStream(bis); message = (Message) in.readObject();
	 * bis.close(); in.close(); } catch (Exception e) { e.printStackTrace(); }
	 * return message; }
	 * 
	 * public static Message receiveMessage(Socket s) { Message message = null;
	 * try { byte[] bytes = new byte[64 * 1024]; s.getInputStream().read(bytes);
	 * message = recover(bytes); } catch (Exception e) { e.printStackTrace(); }
	 * return message; }
	 * 
	 * public String getCommand() { return command; }
	 * 
	 * public String getArg() { return arg; }
	 * 
	 * public List<String> getList() { return list; }
	 * 
	 * public boolean getFlag() { return flag; }
	 * 
	 * public Move getMove() { return move; }
	 * 
	 * public void send(Socket s) { try { s.getOutputStream().write(toBytes());
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * private byte[] toBytes() { byte[] bytes = new byte[64 * 1024]; try {
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out
	 * = new ObjectOutputStream(bos); out.writeObject(this); bytes =
	 * bos.toByteArray(); bos.close(); out.close(); } catch (Exception e) {
	 * e.printStackTrace(); } return bytes; }
	 */
}
