package anandgames.spacegame.pacman;

import java.util.ArrayList;

public class Board {

	private Object[][] actors;
	private Game game;
	// Predefined directions
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int HEIGHT = 21;
	public static final int WIDTH = 21;

	public Board(Game g) {
		setGame(g);
		actors = new Object[HEIGHT][WIDTH];
		// Walls will always be in the same place
		initWalls();
	}

	// Initialize walls in the same place every time
	public void initWalls() {

	}

	// Check if the given coordinates are within the bounds of the board
	public boolean isValid(int row, int col) {
		if (row >= actors.length || row < 0 || col >= actors[0].length
				|| col < 0)
			return false;
		return true;
	}

	// Check if the tile exists
	public boolean isValid(Tile tile) {
		return isValid(tile.getRow(), tile.getCol());
	}

	// Add a to actors
	public void putActor(Actor a) {
		Tile tile = a.getTile();
		if (isValid(tile))
			actors[tile.getRow()][tile.getCol()] = a;
	}

	// Return the actor at the given Tile
	public Actor getActor(Tile tile) {
		if (isValid(tile))
			return (Actor) actors[tile.getRow()][tile.getCol()];
		return null;
	}

	// Remove the actor at the given Tile
	public void removeActor(Tile tile) {
		if (isValid(tile))
			actors[tile.getRow()][tile.getCol()] = null;
	}

	// Return empty Tiles in cardinal directions from given Tile
	public ArrayList<Tile> getValidMoveLocsAt(Tile tile) {
		ArrayList<Tile> moveLocs = new ArrayList<Tile>();
		for (Tile x : getTilesAdjacentTo(tile))
			if (getActor(x) == null)
				moveLocs.add(x);
		return moveLocs;
	}

	// Return all Tiles in cardinal directions adjacent to given tile
	public ArrayList<Tile> getTilesAdjacentTo(Tile tile) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Tile t1 = new Tile(tile.getRow() - 1, tile.getCol());
		if (isValid(t1))
			tiles.add(t1);
		Tile t2 = new Tile(tile.getRow() + 1, tile.getCol());
		if (isValid(t2))
			tiles.add(t2);
		Tile t3 = new Tile(tile.getRow(), tile.getCol() - 1);
		if (isValid(t3))
			tiles.add(t3);
		Tile t4 = new Tile(tile.getRow(), tile.getCol() + 1);
		if (isValid(t4))
			tiles.add(t4);
		return tiles;
	}

	// Get the distance from tile1 to tile2
	public double getLinearDistance(Tile tile1, Tile tile2) {
		int x1 = tile1.getCol(), x2 = tile2.getCol(), y1 = tile1.getRow(), y2 = tile2
				.getRow();
		int d1 = (int) Math.pow(x1 - x2, 2), d2 = (int) Math.pow(y1 - y2, 2);
		return Math.sqrt(d1 + d2);
	}

	// Return the Tile in the given direction and given distance away from
	// origin Tile
	public Tile getTileInDirection(Tile origin, int dir, int distance) {
		switch (dir) {
		case UP:
			Tile t = new Tile(origin.getRow() - distance, origin.getCol());
			if (isValid(t))
				return t;
		case RIGHT:
			Tile t1 = new Tile(origin.getRow(), origin.getCol() + distance);
			if (isValid(t1))
				return t1;
		case DOWN:
			Tile t2 = new Tile(origin.getRow() + distance, origin.getCol());
			if (isValid(t2))
				return t2;
		case LEFT:
			Tile t3 = new Tile(origin.getRow(), origin.getCol() - distance);
			if (isValid(t3))
				return t3;
		default:
			// No valid direction given
			return origin;
		}
	}

	// Return the direction towards tile2 from tile1
	public int getDirectionTowards(Tile tile1, Tile tile2) {
		if (tile2.getRow() > tile1.getRow())
			return DOWN;
		if (tile2.getRow() < tile1.getRow())
			return UP;
		if (tile2.getCol() > tile2.getCol())
			return RIGHT;
		return LEFT;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
