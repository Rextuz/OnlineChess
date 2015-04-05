package com.rextuz.chess.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

public class AuthServer extends UnicastRemoteObject implements AuthServerInterface {
    private static final long serialVersionUID = 1L;
    public static Registry registry;
    private static int PORT;
    private Users users = new Users();
    private Users searching = new Users();

    public AuthServer(int port) throws Exception {
        PORT = port;
    }

    public boolean start() {
        try {
            registry = LocateRegistry.createRegistry(PORT);
            registry.bind("OnlineChess", this);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean login(String name) {
        if (users.add(name))
            return true;
        return false;
    }

    @Override
    public List<String> find(String name) {
        return searching.toList();
    }

    @Override
    public boolean disconnect(String name) {
        if (users.remove(name) || searching.remove(name))
            return true;
        return false;
    }

    @Override
    public String connect(String name) throws RemoteException {
        Random random = new Random();
        List<String> list = searching.toList();
        String foe = list.remove(random.nextInt(list.size()));
        searching.setFoe(foe, name);
        return foe;
    }

    @Override
    public String search(String name) throws RemoteException {
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

    @Override
    public void remove(String name) {
        searching.remove(name);
    }

}
