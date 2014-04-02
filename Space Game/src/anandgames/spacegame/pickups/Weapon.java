package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Weapon {

	private int x, y, radius, ammo, limiter, ammoPerShot;
	private String name;
	private Point spriteKey, bulletKey;
	private Board board;
	private double orientation;
	private Sound fire;

	public Weapon(Board b, int x, int y, String name, Point spriteKey,
			Point bulletKey, int ammo, int ammoPerShot, int limiter, String soundPath) {
		setX(x);
		setY(y);
		setName(name);
		setSpriteKey(spriteKey);
		setBoard(b);
		setOrientation(0);
		setRadius(6);
		setAmmo(ammo);
		setLimiter(limiter);
		setBulletKey(bulletKey);
		setAmmoPerShot(ammoPerShot);
		fire = Gdx.audio.newSound(Gdx.files
				.internal(soundPath));
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

	public Sound getSound() {
		return fire;
	}

	public void setSound(Sound s) {
		fire = s;
	}

	public int getAmmoPerShot() {
		return ammoPerShot;
	}

	public void setAmmoPerShot(int ammoPerShot) {
		this.ammoPerShot = ammoPerShot;
	}

	public Point getBulletKey() {
		return bulletKey;
	}

	public void setBulletKey(Point bulletKey) {
		this.bulletKey = bulletKey;
	}

}
