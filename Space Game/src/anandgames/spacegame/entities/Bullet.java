package anandgames.spacegame.entities;

import java.awt.Point;

import anandgames.spacegame.Board;

public class Bullet extends Entity {

	private Board board;

	public Bullet(int startX, int startY, double orientation,
			Board board) {
		super(startX, startY, 7,0, 0, orientation, 1, new Point (0,2));
		setRadius(2);
		//Bullet is moving as soon as it is fired
		setDx((int) (Math.cos(orientation) * getSpeed()));
		setDy((int) (Math.sin(orientation) * getSpeed()));
		setBoard(board);
		
	}

	public void move() {
		super.move();
	}
	
	public void hit (Entity e) {
		e.setHp(e.getHp() - 1);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
