package com.rextuz.chess.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MatchServerSocket extends Thread {

    private ServerSocket s;
    private int PORT;

    public MatchServerSocket(int port) {
        PORT = port;
        start();
    }

    @Override
    public void run() {
        try {
            s = new ServerSocket(PORT);
            while (true) {
                new ServerThread(s.accept());
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        new MatchServerSocket(4242);
    }

    private class ServerThread extends Thread {

        private Socket s;

        public ServerThread(Socket s) {
            this.s = s;
            start();
        }

        @Override
        public void run() {
            byte[] bytes = new byte[0];
            try {
                s.getInputStream().read(bytes);
                Message message = Message.recover(bytes);
                System.out.println(message.getCommand());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
