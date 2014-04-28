package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class Rifle extends Weapon {

	public Rifle(Board board, Vector2 pos) {
		super(pos, "rifle", new Point(1, 12), new Point(0, 2), 150, 1,
				7, 3, "data/Space Game/Sounds/Laser_Shoot.wav", board);
	}
}
