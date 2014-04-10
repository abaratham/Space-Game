package anandgames.spacegamedesktop;

import anandgames.spacegame.SpaceGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 1280;
		cfg.height = 720;
		cfg.fullscreen = false;
		cfg.vSyncEnabled = true;
		new LwjglApplication(new SpaceGame(), cfg);
	}
}