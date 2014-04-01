package anandgames.spacegame.pacman;

//Used to prevent other Actors from moving to a certain location
public class Wall extends Actor {

	public Wall(Tile t, Board b) {
		super(t, b);
	}
	
	public void move() {
		return;
	}

}
