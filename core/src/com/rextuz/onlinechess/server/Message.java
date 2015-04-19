package com.rextuz.onlinechess.server;

import com.rextuz.onlinechess.pieces.Piece;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Message implements Serializable {
    private String command = null;
    private String arg = null;
    private Piece piece = null;
    private List<String> list = null;
    private boolean flag = false;
    private Move move = null;

    public Message(String command) {
        this.command = command;
    }

    public Message(String command, String arg) {
        this.command = command;
        this.arg = arg;
    }

    public Message(String command, String arg, Piece piece) {
        this.command = command;
        this.arg = arg;
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public Message(String command, List<String> list) {
        this.command = command;
        this.list = list;
    }

    public Message(String command, boolean flag) {
        this.command = command;
        this.flag = flag;
    }

    public Message(String command, Move move) {
        this.command = command;
        this.move = move;
    }

    public static Message recover(byte[] bytes) {
        Message message = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            message = (Message) in.readObject();
            bis.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Message receiveMessage(Socket s) {
        Message message = null;
        try {
            byte[] bytes = new byte[64 * 1024];
            s.getInputStream().read(bytes);
            message = recover(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public List<String> getList() {
        return list;
    }

    public boolean getFlag() {
        return flag;
    }

    public Move getMove() {
        return move;
    }

    public void send(Socket s) {
        try {
            s.getOutputStream().write(toBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] toBytes() {
        byte[] bytes = new byte[64 * 1024];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(this);
            bytes = bos.toByteArray();
            bos.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
