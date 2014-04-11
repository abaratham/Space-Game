package anandgames.spacegame.entities;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	private Board board;

	//Standard "Chaser" enemy
	public Enemy(Vector2 startPos, Board board) {
		super(startPos, 0, 10, new Point(0,1));
		this.setBoard(board);
		setRadius(20);
	}

	//Face the player and move
	public void move() {
		setOrientation(getDirectionTowardShip());
		getVelocity().x = (int) (Math.cos(getOrientation()) * getSpeed());
		getVelocity().y = (int) (Math.sin(getOrientation()) * getSpeed());
		super.move();
	}

	//Find direction toward the player
	public double getDirectionTowardShip() {
		PlayerShip ship = getBoard().getShip();
		float dx = getPosition().x - ship.getPosition().x, dy = getPosition().y - ship.getPosition().y;
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
