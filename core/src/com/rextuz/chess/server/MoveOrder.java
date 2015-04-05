package com.rextuz.chess.server;

import com.rextuz.chess.server.Move;

import java.io.*;
import java.net.Socket;

public class MoveOrder {
    private Move move;
    private String hostname;
    private int port;

    public MoveOrder(String name, int x1, int y1, int x2, int y2, String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        move = new Move(name, x1, y1, x2, y2);
        sendOrder();
    }

    public static Move toObject(byte[] bytes) {
        Move move = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            move = (Move) in.readObject();
            in.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return move;
    }

    private void sendOrder() {
        try {
            Socket s = new Socket(hostname, port);
            byte[] bytes = toBytes();
            s.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] toBytes() {
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(move);
            bytes = bos.toByteArray();
            out.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
