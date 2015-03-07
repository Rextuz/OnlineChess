package com.rextuz.chess.server;

import java.util.ArrayList;
import java.util.List;

public class Users {
	List<User> users;

	private class User {
		private String name;

		private User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

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
}
