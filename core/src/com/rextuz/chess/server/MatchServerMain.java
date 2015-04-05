package com.rextuz.chess.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MatchServerMain extends Thread {

    private ServerSocket socket;
    public static Map<String, Socket> users;

    public MatchServerMain(int port) {
        try {
            users = new HashMap();
            socket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                new MatchServer(socket.accept());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
