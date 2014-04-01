package anandgames.spacegame.entities;

import java.awt.Point;

import anandgames.spacegame.Board;

public class Enemy extends Entity {

	private Board board;

	//Standard "Chaser" enemy
	public Enemy(int startX, int startY, Board board) {
		super(startX, startY, 3, 0, 0, 0, 1);
		this.setBoard(board);
		setRadius(5);
		setCenter(new Point(4,4));
	}

	//Face the player and move
	public void move() {
		setOrientation(getDirectionTowardShip());
		setDx((int) (Math.cos(getOrientation()) * getSpeed()));
		setDy((int) (Math.sin(getOrientation()) * getSpeed()));
		super.move();
	}

	//Find direction toward the player
	public double getDirectionTowardShip() {
		PlayerShip ship = getBoard().getShip();
		int dx = getX() - ship.getX(), dy =getY() - ship.getY();
		double angle = Math.atan2(dy, dx);
			return angle + (Math.PI);

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
