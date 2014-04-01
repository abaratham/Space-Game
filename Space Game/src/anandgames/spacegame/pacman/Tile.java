package anandgames.spacegame.pacman;

import java.awt.Point;

public class Tile {
	private int row, col;
	private Point spriteKey;

	public Tile(int row, int col) {
		setRow(row);
		setCol(col);
	}

	//Two tiles with the same coordinates are considered the same
	public boolean equals(Tile t) {
		if (this.row == t.getRow() && this.col == t.getCol())
			return true;
		else
			return false;
	}

	public Point getSpriteKey() {
		return spriteKey;
	}

	public void setSpriteKey(Point spriteKey) {
		this.spriteKey = spriteKey;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
