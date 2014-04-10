package anandgames.spacegame.entities;

import java.awt.Point;

import com.badlogic.gdx.math.Vector2;

public class Entity {

	private int radius, speed;
	private Point spriteKey;
	private double orientation;
	private boolean isVisible;
	private Vector2 position, velocity;

	public Entity(Vector2 startPos,	double orientation, int speed, Point key) {
		velocity = new Vector2(0,0);
		setPosition(startPos);
		setOrientation(orientation);
		setSpriteKey(key);
		setVisible(true);
		setSpeed(speed);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 pos) {
		position = pos;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 vel) {
		velocity = vel;
	}
	
	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public void move() {
		position.add(velocity);
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setSpriteKey(Point key) {
		spriteKey = key;
	}
	
	public Point getSpriteKey() {
		return spriteKey;
	}

}
