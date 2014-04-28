package anandgames.spacegame.pickups;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class FlameThrower extends Weapon {
	public FlameThrower(Board b, Vector2 pos) {
		super(pos, "flamethrower", new Point(1, 10), new Point(1, 11), 150,
				1, 14, 0, "data/Space Game/Sounds/Flame.wav", b);
	}

}
