package anandgames.spacegame.entities;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class Entity {

	private int radius, maxSpeed;
	private Point spriteKey;
	private double orientation;
	private boolean isVisible;
	private float maxAcceleration;
	private Vector2 position, velocity, acceleration;
	private Board board;

	public Entity(Vector2 startPos, double orientation, int speed,
			float acceleration, Point key, Board b) {
		velocity = new Vector2(0, 0);
		setAcceleration(new Vector2(0, 0));
		maxAcceleration = acceleration;
		setPosition(startPos);
		setOrientation(orientation);
		setSpriteKey(key);
		setVisible(true);
		setMaxSpeed(speed);
		setBoard(b);
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
		if (acceleration.x == 0 && acceleration.y == 0) {
			position.add(velocity);
			return;
		}
		velocity.add(acceleration);
		double currentSpeed = Math.sqrt(Math.pow(velocity.x, 2)
				+ Math.pow(velocity.y, 2));
		if (currentSpeed > maxSpeed)
			currentSpeed = maxSpeed;
		double angle = Math.atan2(velocity.y, velocity.x);
		velocity.x = (float) (Math.cos(angle) * currentSpeed);
		velocity.y = (float) (Math.sin(angle) * currentSpeed);
		position.add(velocity);
		if (position.x > board.getWidth())
			position.x = board.getWidth();
		if (position.y > board.getHeight())
			position.y = board.getHeight();
		if (position.x < 0)
			position.x = 0;
		if (position.y < 0)
			position.y = 0;
	}

	// Get the distance to Entity e
	public float getDistanceTo(Entity e) {
		float deltaX = (this.getPosition().x + this.getRadius())
				- (e.getPosition().x + e.getRadius());

		float deltaY = (this.getPosition().y - this.getRadius())
				- (e.getPosition().y - e.getRadius());

		float dist = (float) Math.sqrt(Math.pow(deltaX, 2)
				+ Math.pow(deltaY, 2));
		return dist;
	}

	// Return whether or not this entity collides with Entity e
	public boolean collidesWith(Entity e) {
		return this.getRadius() + e.getRadius() > this.getDistanceTo(e);
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

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board b) {
		board = b;
	}

}
