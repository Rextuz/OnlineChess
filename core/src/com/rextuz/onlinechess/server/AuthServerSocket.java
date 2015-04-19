package com.rextuz.onlinechess.server;

import com.rextuz.onlinechess.pieces.Piece;

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
                    Message m = Message.recover(bytes);
                    dealWithIt(m);
                } catch (Exception e) {
                    running = false;
                }
            }
        }

        private void dealWithIt(Message m) {
            String command = m.getCommand();
            String arg = m.getArg();
            if (command.equals("login")) {
                boolean f = server.login(arg);
                new Message("login", f).send(s);
            } else if (command.equals("find")) {
                List<String> list = server.find();
                new Message("find", list).send(s);
            } else if (command.equals("disconnect")) {
                boolean f = server.disconnect(arg);
                new Message("disconnect", f).send(s);
            } else if (command.equals("connect")) {
                String str = server.connect(arg);
                new Message("connect", str).send(s);
            } else if (command.equals("search")) {
                String str = server.search(arg);
                new Message("search", str).send(s);
            } else if (command.equals("remove")) {
                server.remove(arg);
                new Message("remove").send(s);
            } else if (command.equals("move")) {
                server.move(m.getMove());
                new Message("move").send(s);
            } else if (command.equals("getMove")) {
                Move move = server.getMove(arg);
                new Message("getMove", move).send(s);
            }
        }

    }
}
