package anandgames.spacegame.entities;

import java.awt.Point;

public class Entity {

	private int x, y, dx, dy, startingHp, hp, radius;
	private double speed;
	private Point center;
	private double orientation;
	private boolean isVisible = true;

	public Entity(int startX, int startY, double speed, int dx, int dy,
			double orientation, int hp) {
		x = startX;
		y = startY;
		this.setSpeed(speed);
		this.dx = dx;
		this.dy = dy;
		this.orientation = orientation;
		startingHp = hp;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public int getStartingHp() {
		return startingHp;
	}

	public void setStartingHp(int startingHp) {
		this.startingHp = startingHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public void rotate(int angle) {
		orientation += angle;
	}

	public void reOrient() {

	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
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

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

}
