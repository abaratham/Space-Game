package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class Shotgun extends Weapon {
	public Shotgun(Board b, Vector2 pos) {
		super(pos, "shotgun", new Point(1, 9), new Point(0, 2), 75, 3, 7,
				14, "data/Space Game/Sounds/Laser_Shoot.wav", b);
	}
}
