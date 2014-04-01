package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

public class Weapon {

	private int x, y, radius, ammo, limiter;
	private String name;
	private Point spriteKey;
	private Board board;
	private double orientation;

	public Weapon(int x, int y, String name, Point key, Board b, int ammo, int limiter) {
		setX(x);
		setY(y);
		setName(name);
		setSpriteKey(key);
		setBoard(b);
		setOrientation(0);
		setRadius(4);
		setAmmo(ammo);
		setLimiter(limiter);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getSpriteKey() {
		return spriteKey;
	}

	public void setSpriteKey(Point spriteKey) {
		this.spriteKey = spriteKey;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	public void setRadius(int r) {
		radius = r;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setAmmo(int a) {
		ammo = a;
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	public void setLimiter(int lim) {
		limiter = lim;
	}
	
	public int getLimiter() {
		return limiter;
	}

}
