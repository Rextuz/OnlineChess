package com.rextuz.chess.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MatchServer extends UnicastRemoteObject implements MatchServerInterface {
    private List<Move> moves = new ArrayList<Move>();
    private int PORT;

    public MatchServer(int port) throws Exception {
        PORT = port;
    }

    public void start() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind("MatchServer", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(Move move) throws RemoteException {
        moves.add(move);
    }

    @Override
    public Move getMove(String name) throws RemoteException {
        while (true) {
            for (Move move : moves)
                if (move.getName().equals(name)) {
                    moves.remove(move);
                    return move;
                }
        }
    }
}
