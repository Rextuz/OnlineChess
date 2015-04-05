package com.rextuz.chess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rextuz.chess.OnlineChess;

public class Launch {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.height = 800;
		config.width = 480;
		new LwjglApplication(new OnlineChess("white", "Rextuz", "FOE", "rextuz-pc", 4242), config);
	}

}
