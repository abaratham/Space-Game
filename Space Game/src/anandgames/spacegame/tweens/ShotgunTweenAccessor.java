package anandgames.spacegame.tweens;

import anandgames.spacegame.pickups.Weapon;
import aurelienribon.tweenengine.TweenAccessor;

public class ShotgunTweenAccessor implements TweenAccessor<Weapon> {

	@Override
	public int getValues(Weapon target, int type, float[] returnValues) {
		returnValues[0] = (float) target.getOrientation();
		return 1;
	}

	@Override
	public void setValues(Weapon target, int type, float[] newValues) {
		target.setOrientation(newValues[0]);
	}

}
