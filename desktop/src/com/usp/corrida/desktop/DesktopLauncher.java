package com.usp.corrida.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.usp.corrida.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Corrida Aritmética - Versão Desktop para testes";
		config.useGL30 = true;
		config.height = 540;
		config.width = 960;

		new LwjglApplication(new Core(), config);
	}
}
