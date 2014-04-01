package anandgames.spacegame.pacman;

import java.util.ArrayList;

public class Ghost extends Actor {

	private Tile targetTile, scatterTile;
	private Tile nextTile;
	private String name;

	public Ghost(Tile tile, Tile scatterTile, Board b) {
		super(tile, b);
		setScatterTile(scatterTile);
	}

	// Return the index of the lowest double in the given array
	public static int findSmallestDouble(double[] nums) {
		int index = 0;
		int targetIndex = 0;
		while (index < nums.length - 1) {
			if (nums[index + 1] < nums[targetIndex]) {
				targetIndex = index + 1;
			}
			index++;
		}
		return targetIndex;
	}

	// Choose next move location, which will be the tile which is the closest to
	// the Ghost's target Tile
	public void chooseNextMove() {
		ArrayList<Tile> moveLocs = getBoard().getValidMoveLocsAt(
				getBoard().getTileInDirection(getTile(), getDirection(), 1));
		assert (moveLocs.size() != 0);
		double[] distances = new double[moveLocs.size()];
		int index = 0;
		for (Tile x : moveLocs) {
			double dist = getBoard().getLinearDistance(x, targetTile);
			distances[index] = dist;
			index++;
		}
		setNextTile(moveLocs.get(findSmallestDouble(distances)));
	}

	// Orient towards the planned move, move, update target Tile based on
	// Pacman's location, choose next move
	public void act() {
		setDirection(getBoard().getDirectionTowards(getTile(), nextTile));
		move();
		chooseTargetTile();
		chooseNextMove();
	}

	//Update target based on Pacman's location
	public void chooseTargetTile() {
		Actor pacman = getBoard().getGame().getPacman();
		switch (name) {
		case "Blinky":
			setTargetTile(pacman.getTile());
			break;
		case "Pinky":
			setTargetTile(getBoard().getTileInDirection(pacman.getTile(),
					pacman.getDirection(), 4));
			break;
		case "Inky":
			Tile blinky = getBoard().getGame().getBlinky().getTile();
			int dir = getBoard().getDirectionTowards(blinky, pacman.getTile());
			setTargetTile(getBoard().getTileInDirection(
					blinky,
					dir,
					(int) getBoard()
							.getLinearDistance(blinky, pacman.getTile())));
			break;
		case "Pokey":
			double dist = getBoard().getLinearDistance(getTile(),
					pacman.getTile());
			if (dist < 8) {
				setTargetTile(pacman.getTile());
				break;
			}
			setTargetTile(scatterTile);
			break;

		}
	}

	public Tile getTargetTile() {
		return targetTile;
	}

	public void setTargetTile(Tile targetTile) {
		this.targetTile = targetTile;
	}

	public Tile getNextTile() {
		return nextTile;
	}

	public void setNextTile(Tile nextTile) {
		this.nextTile = nextTile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tile getScatterTile() {
		return scatterTile;
	}

	public void setScatterTile(Tile scatterTile) {
		this.scatterTile = scatterTile;
	}

	public static void main(String[] args) {
		double[] nums = { 1.3, 3.4, .2 };
		System.out.println(Ghost.findSmallestDouble(nums));
	}

}
