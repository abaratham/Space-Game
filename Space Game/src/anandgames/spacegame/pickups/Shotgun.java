package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

public class Shotgun extends Weapon {
	public Shotgun(Board b, float x, float y) {
		super(b, x, y, "shotgun", new Point(1, 9), new Point(0, 2), 75, 3, 14,
				"data/Space Game/Sounds/Laser_Shoot.wav");
	}
}
