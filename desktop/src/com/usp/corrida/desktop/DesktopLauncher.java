package com.usp.corrida.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.usp.corrida.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Corrida Aritmética";
		config.useGL30 = true;
		config.height = 480;
		config.width = 760;

		new LwjglApplication(new Core(), config);
	}
}
