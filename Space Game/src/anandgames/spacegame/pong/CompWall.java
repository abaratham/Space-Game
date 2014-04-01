package anandgames.spacegame.pong;

public class CompWall extends Wall {

	public int targetLoc;

	public CompWall(int x, int y, double speed, int height, Pong p) {
		super(x, y, speed, height, p);
		setDx(0);
		setDy(0);
		targetLoc = getY();
	}

	//Calculate y coordinate of where the ball will land
	public int getTargetLoc() {
		Ball b = getPong().getBall();
		double x = Math.abs(b.getDx());
		double y = b.getDy();
		int newTarget = (int) Math
				.abs(((getPong().getBall().getX() * (y / x))));
		if (y < 0)
			setTargetLoc(b.getY() - newTarget);
		else
			setTargetLoc(b.getY() + newTarget);
		return targetLoc;
	}

	//Move towards targetLoc
	public void move() {
		if (targetLoc > getY())
			setDy((int) getSpeed());
		if (targetLoc < getY())
			setDy((int) -getSpeed());
		if (targetLoc >= getY() && targetLoc <= getY() + 45)
			setDy(0);
		super.move();
		if (getY() >= getPong().getHeight())
			setY(getPong().getHeight());
		if (getY() <= 0)
			setY(0);
	}

	public void setTargetLoc(int targetLoc) {
		this.targetLoc = targetLoc;
	}

}
