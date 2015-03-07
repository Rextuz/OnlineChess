package com.rextuz.chess.server;

import java.util.List;

public interface ServerSend {
	
	public boolean login(String name);

	public List<String> find(String name);
	
	public boolean connect(String name);

	public boolean disconnect(String name);
	
}
