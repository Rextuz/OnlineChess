package com.rextuz.chess.server;

import java.io.*;
import java.net.Socket;

public class Message implements Serializable {
    private String command;

    public Message(String command, Socket s) {
        this.command = command;
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

    public String getCommand() {
        return command;
    }

    public void send(Socket s) {
        try {
            s.getOutputStream().write(toBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] toBytes() {
        byte[] bytes = new byte[64*1024];
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
