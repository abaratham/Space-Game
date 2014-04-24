package anandgames.spacegame.entities;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import anandgames.spacegame.Board;
import anandgames.spacegame.pickups.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Entity {

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private boolean upPressed, rightPressed, leftPressed, downPressed,
			mouseHeld, shielded;
	private int score, currentAmmo;
	private Sound fire;
	private Weapon weapon;

	public PlayerShip(Board board) {
		// Always initialized at the center of the board
		super(new Vector2(board.getWidth() / 2, board.getHeight() / 2), 0, 15,
				2f, new Point(), board);
		setRadius(20);
		setScore(0);
		// Initialize default fire sound byte
		fire = Gdx.audio.newSound(Gdx.files
				.internal("data/Space Game/Sounds/Laser_Shoot.wav"));
		// Start with infinite ammo
		currentAmmo = 0;
		setMouseHeld(false);

	}

	// Fire a new bullet and add it to the ship's bullets list
	public void fire() {
		// Fire current weapon and subtract from ammo
		// If weapon is null, use default weapon
		if (weapon == null || weapon.getAmmoPerShot() == 1) {
			bullets.add(new Bullet(new Vector2(getPosition().x + 2,
					getPosition().y + 2), getOrientation(), getBoard()));
		}
		// else fire equipped weapon
		else {
			// Make sure the ship doesn't fire more ammo than it has
			int limit;
			if (currentAmmo < weapon.getAmmoPerShot())
				limit = currentAmmo;
			else
				limit = weapon.getAmmoPerShot();
			for (int i = 0; i < limit; i++) {
				bullets.add(new Bullet(new Vector2(getPosition().x
						+ getRadius(), getPosition().y + getRadius()),
						getOrientation() + ((Math.PI / 12) * (i - 1)),
						getBoard()));
			}
			currentAmmo -= limit;

			// If out of ammo set weapon to default
			if (currentAmmo <= 0) {
				setWeapon(null);
			}
		}
		// Play correct sound
		Sound sound;
		if (weapon == null)
			sound = fire;
		else
			sound = weapon.getSound();
		sound.play();
	}

	// Fire a bullet when the mouse is pressed
	public void mousePressed() {
		fire();
		setMouseHeld(true);
	}

	// Move the ship and limit movement to within the bounds of the board
	public void move() {
		super.move();
		if (getPosition().x > getBoard().getWidth())
			getPosition().x = getBoard().getWidth();
		if (getPosition().x < 0)
			getPosition().x = 0;
		if (getPosition().y > getBoard().getHeight())
			getPosition().y = getBoard().getHeight();
		if (getPosition().y < 0)
			getPosition().y = 0;
	}

	public void mouseReleased() {
		setMouseHeld(false);
	}

	// Handle key input
	public void keyPressed(char key) {

		if (key == 'a') {
			getAcceleration().x = -getMaxAcceleration();
			leftPressed = true;
		}

		if (key == 'd') {
			getAcceleration().x = getMaxAcceleration();
			rightPressed = true;
		}

		if (key == 'w') {
			getAcceleration().y = getMaxAcceleration();
			upPressed = true;
		}

		if (key == 's') {
			getAcceleration().y = -getMaxAcceleration();
			downPressed = true;
		}

	}

	public void keyReleased(char key) {

		if (key == 'a') {
			if (!rightPressed) {
				getAcceleration().x = 0;
			}
			if (rightPressed)
				getAcceleration().x = getMaxAcceleration();
			leftPressed = false;
		}

		if (key == 'd') {
			if (!leftPressed) {
				getAcceleration().x = 0;
			}
			if (leftPressed) {
				getAcceleration().x = -getMaxAcceleration();
			}
			rightPressed = false;
		}

		if (key == 'w') {
			if (!downPressed) {
				getAcceleration().y = 0;
			}
			if (downPressed)
				getAcceleration().y = -getMaxAcceleration();
			upPressed = false;
		}

		if (key == 's') {
			if (!upPressed) {
				getAcceleration().y = 0;
			}
			if (upPressed)
				getAcceleration().y = getMaxAcceleration();
			downPressed = false;
		}

		if (key == KeyEvent.VK_R) {
			getBoard().reset();
		}

	}

	// Orient the ship to face the mouse
	public void reOrient() {
		Point m = MouseInfo.getPointerInfo().getLocation();
		float angle = (float) (Math.atan2(Gdx.graphics.getHeight() - m.y
				- (Gdx.graphics.getHeight() / 2),
				m.x - (Gdx.graphics.getWidth() / 2)));
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

	public void setCurrentAmmo(int ammo) {
		currentAmmo = ammo;
	}

	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public boolean isShielded() {
		return shielded;
	}

	public void setShielded(boolean s) {
		shielded = s;
	}

}
