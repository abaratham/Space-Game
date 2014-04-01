package anandgames.spacegame.pacman;

public class Actor {
	private int direction;
	private Tile tile;
	private Board board;

	public Actor(Tile t, Board b) {
		setBoard(b);
		setTile(t);
		b.putActor(this);
	}

	// Turn clockwise once
	public void turn() {
		direction++;
	}

	//Move forward one tile
	public void move() {
		//Check if the tile in front of the Actor is empty
		if (board.getActor(board.getTileInDirection(tile, getDirection(), 1)) == null) {
			board.removeActor(tile);
			setTile(board.getTileInDirection(tile, getDirection(), 1));
			board.putActor(this);
		}
	}

	//To be defined for each Actor
	public void act() {

	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
