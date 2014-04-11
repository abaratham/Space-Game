package anandgames.spacegame.entities;

import java.awt.Point;

import com.badlogic.gdx.math.Vector2;

public class Entity {

	private int radius, maxSpeed;
	private Point spriteKey;
	private double orientation;
	private boolean isVisible;
	private float maxAcceleration;
	private Vector2 position, velocity, acceleration;

	public Entity(Vector2 startPos, double orientation, int speed,
			float acceleration, Point key) {
		velocity = new Vector2(0, 0);
		setAcceleration(new Vector2(0, 0));
		maxAcceleration = acceleration;
		setPosition(startPos);
		setOrientation(orientation);
		setSpriteKey(key);
		setVisible(true);
		setMaxSpeed(speed);
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int s) {
		maxSpeed = s;
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
//		velocity.x += acceleration.x;
//		velocity.y += acceleration.y;
		velocity.add(acceleration);
		if (velocity.x < -maxSpeed) {
			velocity.x = -maxSpeed;
		}
		if (velocity.x > maxSpeed) {
			velocity.x = maxSpeed;
		}
		if (velocity.y < -maxSpeed) {
			velocity.y = -maxSpeed;
		}
		if (velocity.y > maxSpeed) {
			velocity.y = maxSpeed;
		}
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

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public float getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(float maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

}
