package anandgames.spacegame.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class ShootingEnemy extends Enemy {

	private int counter;
	private ArrayList<Bullet> bullets;

	public ShootingEnemy(Vector2 startPos, Board b) {
		super(startPos, b);
		bullets = new ArrayList<Bullet>();
		// TODO: draw a shooting enemy sprite at 1, 13 in Sprites.png
		setSpriteKey(new Point(1, 13));
	}

	// Set random orientation within 90 degrees of direction toward ship every
	// 20 iterations, fire a bullet every 10
	// iterations
	public void move() {
		// every 20 iterations move in a new direction
		if (counter == 0) {
			Random r = new Random();
			int dir2ship = (int) Math.toDegrees(getDirectionTowardShip());
			int orientation = r.nextInt(180) - 90;
			setOrientation(orientation + dir2ship);
		}

		// fire a bullet every 10 iterations
		if (counter % 10 == 0) {
			fire();
		}
		// reset counter every 20 iterations
		if (counter == 20)
			counter = 0;
		counter++;
	}

	public void fire() {
		Random r = new Random();
		int angleOffset = r.nextInt(45);
		int angle = (int) (getDirectionTowardShip() + angleOffset);
		bullets.add(new Bullet(new Vector2(getPosition().x + getRadius(),
				getPosition().y + getRadius()), angle, getBoard()));
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}
