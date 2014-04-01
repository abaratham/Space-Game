package anandgames.spacegame.pong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Pong implements ActionListener {

	private Wall player;
	private CompWall computer;
	private Ball ball;
	private int width, height;
	private Timer timer;
	private boolean inGame;

	public Pong() {
		setWidth(1024);
		setHeight(512);
		setPlayer(new Wall(1024, 256, 8, 45, this));
		setComputer(new CompWall(0, 256, 8, 45, this));
		setBall(new Ball(512, 256, 11, 5, this));
		timer = new Timer(33, this);
		setInGame(true);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		update();
	}

	// Move each wall and the ball, check for collisions between walls and the
	// ball
	public void update() {
		if (inGame) {
			player.move();
			ball.move();
			computer.move();
			checkBallCollisions();

		}
	}

	// Check for and handle collisions
	public void checkBallCollisions() {
		int ballRadius = ball.getRadius();
		// Real boundaries of sections of each wall
		int ballX = ball.getX(), ballY = ball.getY(), player1 = player.getY()
				+ player.getThirdBoundary(1), player2 = player.getY()
				+ player.getThirdBoundary(2), player3 = player.getY()
				+ player.getThirdBoundary(3), comp1 = computer.getY()
				+ computer.getThirdBoundary(1), comp2 = computer.getY()
				+ computer.getThirdBoundary(2), comp3 = computer
				.getThirdBoundary(3) + computer.getY();

		// Ball is at the player's side
		if (ballX + ballRadius >= player.getX()) {

			// Ball hit top third of the player Wall
			if (ballY + ballRadius >= player.getY()
					&& ballY + ballRadius <= player1) {
				System.out.println("1");
				ball.setDx(ball.getDx() * -1);
				ball.setDy(ball.getDy() - 3);
				computer.getTargetLoc();
				return;
			}

			// Ball hit the middle third of the player Wall
			if (ballY + ballRadius > player1 && ballY + ballRadius <= player2) {
				ball.setDx(ball.getDx() * -1);
				System.out.println("2");
				computer.getTargetLoc();
				return;
			}

			// Ball hit the lower third of the player Wall
			if (ballY + ballRadius > player2 && ballY + ballRadius <= player3) {
				System.out.println(3);
				ball.setDx(ball.getDx() * -1);
				ball.setDy(ball.getDy() + 3);
				computer.getTargetLoc();
				return;
			}

			// Ball got past the player Wall, increment computer score and reset
			// game
			if (ballY < player.getY()
					|| ballY > player.getY() + player.getHeight()) {
				computer.setScore(computer.getScore() + 1);
				reset();
				return;
			}
		}

		// Ball is at the computer side
		if (ballX <= computer.getX() + 10) {

			// Ball hit top third of the computer Wall
			if (ballY >= computer.getY() && ballY <= comp1) {
				ball.setDx(ball.getDx() * -1);
				ball.setDy(ball.getDy() - 3);
				computer.setTargetLoc(256);
				return;
			}

			// Ball hit middle third of the computer Wall
			if (ballY > comp1 && ballY <= comp2) {
				ball.setDx(ball.getDx() * -1);
				computer.setTargetLoc(256);
				return;
			}

			// Ball hit the bottom third of the computer Wall
			if (ballY > comp2 && ballY <= comp3) {
				ball.setDx(ball.getDx() * -1);
				ball.setDy(ball.getDy() + 3);
				computer.setTargetLoc(256);
				return;
			}

			// Ball got past the computer, increment player score and reset game
			if (ballY < computer.getY()
					|| ballY > computer.getY() + computer.getHeight()) {
				player.setScore(player.getScore() + 1);
				reset();
				return;
			}
		}

	}
	
	//Reset the game
	public void reset() {
		ball.reset(512, 256);
		computer.reset(256);
		player.reset(256);
		computer.setTargetLoc(256);
	}

	public void setBall(Ball b) {
		ball = b;
	}

	public Ball getBall() {
		return ball;
	}

	public CompWall getComputer() {
		return computer;
	}

	public void setComputer(CompWall computer) {
		this.computer = computer;
	}

	public Wall getPlayer() {
		return player;
	}

	public void setPlayer(Wall player) {
		this.player = player;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

}
