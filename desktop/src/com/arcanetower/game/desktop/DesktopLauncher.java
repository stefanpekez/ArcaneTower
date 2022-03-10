package com.arcanetower.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arcanetower.game.ArcaneTower;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Arcane Tower";
		config.width = ArcaneTower.SCREEN_WIDTH;
		config.height = ArcaneTower.SCREEN_HEIGTH;
		config.resizable = false;
		config.addIcon("appIcon.png", null);
		
		new LwjglApplication(new ArcaneTower(), config);
	}
}
