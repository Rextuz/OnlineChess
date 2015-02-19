package com.rextuz.chess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rextuz.chess.OnlineChess;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.height = 800;
		config.width = 480;
		//config.resizable = false;
		new LwjglApplication(new OnlineChess("white"), config);
	}

}
