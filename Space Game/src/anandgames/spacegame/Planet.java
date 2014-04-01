package anandgames.spacegame;

import com.badlogic.gdx.Screen;

public class Planet {

	private int x, y, radius;
	private Screen screen;
	
	
	//Create a Planet with a screen to be launched upon entry
	public Planet(int x, int y, int radius, Screen screen) {
		setX(x);
		setY(y);
		setRadius(radius);
		setScreen(screen);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

}
