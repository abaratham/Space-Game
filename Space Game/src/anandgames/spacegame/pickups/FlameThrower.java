package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

public class FlameThrower extends Weapon {
	public FlameThrower(Board b, float x, float y) {
		super(b, x, y, "flamethrower", new Point(1, 10), new Point(1, 11), 150,
				1, 1, "data/Space Game/Sounds/Flame.wav");
	}

}
