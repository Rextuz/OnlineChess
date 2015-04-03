package com.rextuz.chess.server;

import java.rmi.RemoteException;

public class MatchServer implements MatchServerInterface {

    private String name1, name2;

    public MatchServer(String n1, String n2) {
        name1 = n1;
        name2 = n2;
    }

    @Override
    public void move(String name, int x, int y, int x1, int y1) throws RemoteException {

    }

}
