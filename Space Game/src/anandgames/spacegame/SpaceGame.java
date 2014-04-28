package anandgames.spacegame;

import anandgames.spacegame.screens.MainMenu;

import com.badlogic.gdx.Game;

public class SpaceGame extends Game {
	public static final String VERSION_NUMBER = "v0.7.2";

	public void create() {
		setScreen(new MainMenu());
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void render() {
		super.render();
	}

	public void pause() {
		super.pause();
	}

	public void resume() {
		super.resume();
	}

	public void dispose() {
		super.dispose();
	}

}