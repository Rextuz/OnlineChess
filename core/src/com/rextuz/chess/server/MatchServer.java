package com.rextuz.chess.server;

import java.io.InputStream;
import java.net.Socket;

public class MatchServer extends Thread {

    private Socket s;

    public MatchServer(Socket s) {
        this.s = s;
        start();
    }

    @Override
    public void run() {
        register();
        while (true) {
            try {
                InputStream is = s.getInputStream();
                byte buf[] = new byte[64 * 1024];
                is.read(buf);
                Move move = MoveOrder.toObject(buf);
                Socket destSocket = MatchServerMain.users.get(move.getName());
                destSocket.getOutputStream().write(buf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void register() {
        try {
            InputStream is = s.getInputStream();
            byte buf[] = new byte[64 * 1024];
            int r = is.read(buf);
            String name = new String(buf, 0, r);
            MatchServerMain.users.put(name, s);
        } catch (Exception e) {

        }
    }
}
