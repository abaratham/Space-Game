package anandgames.spacegame.pong;

import anandgames.spacegame.entities.Entity;

public class Ball extends Entity {

	private int radius;
	Pong pong;

	public Ball(int startX, int startY, double speed, int r, Pong p) {
		super(startX, startY, speed, 3, 0, 0, 0);
		radius = r;
		pong = p;
		setDx((int) getSpeed());
	}

	public void setRadius(int r) {
		radius = r;
	}

	// Put the ball back in the center of the board
	public void reset(int startX, int startY) {
		setX(startX);
		setY(startY);
		setDx((int) getSpeed());
		setDy(0);
	}

	// Make the ball "bounce" when it hits the top or bottom of the board.
	// Tell the computer to recalculate the destination of the ball after a
	// "bounce"
	public void move() {
		if (getY() >= pong.getHeight() || getY() <= 0) {
			setDy(getDy() * -1);
			if (getDx() < 0)
				pong.getComputer().getTargetLoc();
		}
		super.move();
	}

	public int getRadius() {
		return radius;
	}

}
