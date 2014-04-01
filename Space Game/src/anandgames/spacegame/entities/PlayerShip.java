package anandgames.spacegame.entities;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import anandgames.spacegame.Board;
import anandgames.spacegame.pickups.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class PlayerShip extends Entity {

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private boolean upPressed, rightPressed, leftPressed, downPressed,
			mouseHeld;
	private int score, currentAmmo;
	private Board board;
	private Sound fire;
	private Weapon weapon;

	public PlayerShip(Board board) {
		// Always initialized at the center of the board
		super(1024, 1024, 2, 0, 0, 0, 5);
		setCenter(new Point(4, 4));
		setRadius(4);
		setScore(0);
		this.board = board;
		// Initialize fire sound byte
		fire = Gdx.audio.newSound(Gdx.files
				.internal("data/Space Game/Sounds/Laser_Shoot.wav"));
		// Start with infinite ammo
		currentAmmo = -1;

	}

	// Fire a new bullet and add it to the ship's bullets list
	public void fire() {
		// Fire current weapon and subtract from ammo
		// If weapon is null, use default weapon
		if (weapon == null) {
			bullets.add(new Bullet(getX() + 2, getY() + 2, getOrientation(),
					getBoard(), this));
		}
		// else fire equipped weapon
		else {
			switch (weapon.getName()) {
			case "shotgun":
				int limit;
				if (currentAmmo < 3)
					limit = currentAmmo;
				else
					limit = 3;
				for (int i = 0; i < limit; i++) {
					bullets.add(new Bullet(getX() + getRadius(), getY()
							+ getRadius(), getOrientation()
							+ ((Math.PI / 12) * (i - 1)), getBoard(), this));
				}
				currentAmmo -= limit;
				break;
			default:
				break;
			}
			if (currentAmmo == 0) {
				setWeapon(null);
			}
		}
		fire.play();
	}

	private Board getBoard() {
		return board;
	}

	// Fire a bullet when the mouse is pressed
	public void mousePressed() {
		fire();
		setMouseHeld(true);
	}

	// Move the ship and limit movement to within the bounds of the board
	public void move() {
		super.move();
		if (getX() > board.getWidth())
			setX(board.getWidth());
		if (getX() < 0)
			setX(0);
		if (getY() > board.getHeight())
			setY(board.getHeight());
		if (getY() < 0)
			setY(0);
	}

	public void mouseReleased() {
		setMouseHeld(false);
	}

	// Handle key input
	public void keyPressed(char key) {

		if (key == 'a') {
			setDx((int) -getSpeed());
			leftPressed = true;
		}

		if (key == 'd') {
			setDx((int) getSpeed());
			rightPressed = true;
		}

		if (key == 'w') {
			setDy((int) getSpeed());
			upPressed = true;
		}

		if (key == 's') {
			setDy((int) -getSpeed());
			downPressed = true;
		}

	}

	public void keyReleased(char key) {

		if (key == 'a') {
			if (!rightPressed) {
				setDx(0);
			}
			if (rightPressed)
				setDx((int) getSpeed());
			leftPressed = false;
		}

		if (key == 'd') {
			if (!leftPressed) {
				setDx(0);
			}
			if (leftPressed) {
				setDx((int) -getSpeed());
			}
			rightPressed = false;
		}

		if (key == 'w') {
			if (!downPressed) {
				setDy(0);
			}
			if (downPressed)
				setDy((int) -getSpeed());
			upPressed = false;
		}

		if (key == 's') {
			if (!upPressed) {
				setDy(0);
			}
			if (upPressed)
				setDy((int) getSpeed());
			downPressed = false;
		}

		if (key == KeyEvent.VK_R) {
			getBoard().reset();
		}

	}

	// Orient the ship to face the mouse
	public void reOrient() {
		Point m = MouseInfo.getPointerInfo().getLocation();
		float angle = (float) (Math.atan2(720 - m.y - 360, m.x - 640));
		setOrientation(angle);
	}

	public boolean isMouseHeld() {
		return mouseHeld;
	}

	public void setMouseHeld(boolean mouseHeld) {
		this.mouseHeld = mouseHeld;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setWeapon(Weapon w) {
		if (w != null)
		currentAmmo += w.getAmmo();
		weapon = w;
	}

	public Weapon getWeapon() {
		return weapon;
	}

}
