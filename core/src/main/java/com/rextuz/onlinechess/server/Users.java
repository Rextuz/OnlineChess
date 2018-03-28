package com.rextuz.onlinechess.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Users {
    List<User> users;

    public Users() {
        users = new ArrayList<User>();
    }

    public boolean add(String name) {
        if (getUser(name) == null) {
            users.add(new User(name));
            return true;
        }
        return false;
    }

    public boolean remove(String name) {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getName().equals(name)) {
                users.remove(i);
                return true;
            }
        return false;
    }

    public List<String> toList() {
        List<String> result = new ArrayList<String>();
        for (User user : users)
            result.add(user.getName());
        return result;
    }

    private User getUser(String name) {
        for (User user : users)
            if (user.getName().equals(name))
                return user;
        return null;
    }

    public void setFoe(String name, String foe) {
        getUser(name).setFoe(foe);
    }

    public String getFoe(String name) {
        return getUser(name).getFoe();
    }

    public void setSocket(String name, Socket s) {
        getUser(name).setSocket(s);
    }

    public Socket getSocket(String name) {
        return getUser(name).getSocket();
    }

    private class User {
        private String name;
        private String foe = null;
        private Socket s;

        private User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getFoe() {
            return foe;
        }

        public void setFoe(String foe) {
            this.foe = foe;
        }

        public Socket getSocket() {
            return s;
        }

        public void setSocket(Socket s) {
            this.s = s;
        }

    }

}
