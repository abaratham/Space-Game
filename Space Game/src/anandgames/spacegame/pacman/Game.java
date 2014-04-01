package anandgames.spacegame.pacman;

//TODO!!!!
public class Game {
	
	private Board board;
	private Actor pacman;
	private Ghost blinky, pinky, inky, pokey;
	
	public Game (Board b, Actor pacman, Ghost blinky, Ghost pinky, Ghost inky, Ghost pokey) {
		
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Actor getPacman() {
		return pacman;
	}

	public void setPacman(Actor pacman) {
		this.pacman = pacman;
	}

	public Ghost getBlinky() {
		return blinky;
	}

	public void setBlinky(Ghost blinky) {
		this.blinky = blinky;
	}

	public Ghost getPinky() {
		return pinky;
	}

	public void setPinky(Ghost pinky) {
		this.pinky = pinky;
	}

	public Ghost getInky() {
		return inky;
	}

	public void setInky(Ghost inky) {
		this.inky = inky;
	}

	public Ghost getPokey() {
		return pokey;
	}

	public void setPokey(Ghost pokey) {
		this.pokey = pokey;
	}

}
