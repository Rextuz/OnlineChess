package com.rextuz.chess.server;

import java.net.ServerSocket;

public class AuthServerSocket extends Thread {
    private ServerSocket s;
    private int PORT;

    public AuthServerSocket(int port) {
        PORT = port;
        try {
            s = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            try {
                s.accept();
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
}
