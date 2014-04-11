package anandgames.spacegame.entities;

import java.awt.Point;

import anandgames.spacegame.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {

	private Board board;

	public Bullet(Vector2 startPos, double orientation,
			Board board) {
		super(startPos, orientation, 25, new Point (0,2));
		setRadius(7);
		//Bullet is moving as soon as it is fired
		getVelocity().x = (float) (Math.cos(orientation) * getSpeed());
		getVelocity().y = (float) (Math.sin(orientation) * getSpeed());
		setBoard(board);
		
	}

	public void move() {
		super.move();
		int x = Gdx.graphics.getWidth();
		System.out.println(x);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
