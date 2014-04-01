package anandgames.spacegame.pong;

import anandgames.spacegame.entities.Entity;

public class Wall extends Entity {
	boolean upPressed, downPressed;
	private int height, score;
	private Pong pong;

	public Wall(int startX, int startY, double speed, int height, Pong p) {
		super(startX, startY, speed, 0, 0, 0, 0);
		this.height = height;
		setPong(p);
	}

	//Handle input
	public void keyPressed(char key) {

		if (key == 'w') {
			setDy((int) getSpeed());
			upPressed = true;
		}

		if (key == 's') {
			setDy((int) -getSpeed());
			downPressed = true;
		}

	}

	public void keyReleased(char key) {

		if (key == 'w') {
			if (!downPressed) {
				setDy(0);
			}
			if (downPressed)
				setDy((int) -getSpeed());
			upPressed = false;
		}

		if (key == 's') {
			if (!upPressed) {
				setDy(0);
			}
			if (upPressed)
				setDy((int) getSpeed());
			downPressed = false;
		}

	}

	//Move the wall, limit movement to within the bounds of the screen
	public void move() {
		super.move();
		if (getY() >= pong.getHeight() - 45)
			setY(pong.getHeight() - 45);
		if (getY() <= 0)
			setY(0);
	}

	//Return boundary at the bottom of the given section of the wall
	public int getThirdBoundary(int section) {
		if (section > 3 || section < 0)
			return -1;
		if (section == 0)
			return -5;
		if (section == 3)
			return height + 5;
		return (height / 3) * section;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScore() {
		return score;
	}

	public void reset(int y) {
		setY(y);
		setDy(0);
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Pong getPong() {
		return pong;
	}

	public void setPong(Pong p) {
		this.pong = p;
	}

}
