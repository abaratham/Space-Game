package anandgames.spacegame.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class Tetris implements ActionListener {

	private TetrisBoard board;
	private ArrayList<TetrisPiece> pieces;
	private Timer timer;
	private TetrisPiece currentPiece;
	private int delay, score, level, targetScore;

	public static final TetrisPiece L = new TetrisPiece(new int[][] { { 1, 0 },
			{ 1, 0 }, { 1, 1 } });

	public static final TetrisPiece BACK_L = new TetrisPiece(new int[][] {
			{ 0, 1 }, { 0, 1 }, { 1, 1 } });

	public static final TetrisPiece T = new TetrisPiece(new int[][] {
			{ 1, 1, 1 }, { 0, 1, 0 } });

	public static final TetrisPiece LINE = new TetrisPiece(new int[][] { { 1,
			1, 1, 1 } });

	public static final TetrisPiece Z = new TetrisPiece(new int[][] {
			{ 1, 1, 0 }, { 0, 1, 1 } });

	public static final TetrisPiece BACK_Z = new TetrisPiece(new int[][] {
			{ 0, 1, 1 }, { 1, 1, 0 } });

	public static final TetrisPiece BOX = new TetrisPiece(new int[][] {
			{ 1, 1 }, { 1, 1 } });

	public Tetris() {
		pieces = new ArrayList<TetrisPiece>();
		pieces.add(LINE);
		pieces.add(L);
		pieces.add(BACK_L);
		pieces.add(T);
		pieces.add(Z);
		pieces.add(BACK_Z);
		pieces.add(BOX);
		delay = 600;
		score = 0;
		timer = new Timer(delay, this);
		timer.start();
		board = new TetrisBoard();
		newPiece();
		targetScore = 500;
	}

	//Return row numbers of tetrii
	public ArrayList<Integer> checkTetris() {
		int x = 0;
		ArrayList<Integer> tetrii = new ArrayList<Integer>();
		for (int i = TetrisBoard.HEIGHT - 1; i > 0; i--) {
			for (int j = 0; j < TetrisBoard.WIDTH; j++) {
				if (board.getCell(i, j) == 0)
					break;
				x++;
			}
			if (x == TetrisBoard.WIDTH)
				tetrii.add(i);
			x = 0;
		}
		return tetrii;
	}

	//Spawn a randomly selected piece
	public void newPiece() {
		Random r = new Random();
		int x = r.nextInt(7);
		int[][] config = new int[0][0];
		switch (x) {
		case 0:
			config = LINE.getConfig();
			break;
		case 1:
			config = L.getConfig();
			break;
		case 2:
			config = BACK_L.getConfig();
			break;
		case 3:
			config = Z.getConfig();
			break;
		case 4:
			config = BACK_Z.getConfig();
			break;
		case 5:
			config = T.getConfig();
			break;
		case 6:
			config = BOX.getConfig();
			break;
		}
		//Check for a tetris and clear any appropriate rows
		ArrayList<Integer> tetrii = checkTetris();
		for (int i = 0; i < tetrii.size(); i++) {
			board.clearRow(tetrii.get(0));
		}
		switch (tetrii.size()) {
		case 1:
			addScore(40 * (level + 1));
			break;
		case 2:
			addScore(100 * (level + 1));
			break;
		case 3:
			addScore(300 * (level + 1));
			break;
		case 4:
			addScore(1200 * (level + 1));
			break;
		}
		checkLevel();
		System.out.println("CLEARED");
		printBoard();
		setCurrentPiece(new TetrisPiece(config));
		board.putPiece(getCurrentPiece());
	}

	//Check and update level if necessary
	private void checkLevel() {
		if (score >= targetScore) {
			level++;
			targetScore *= 2;
			delay -= 50;
			timer.setDelay(delay);
		}
	}

	public void movePiece(int dx, int dy) {
		board.clearPiece(getCurrentPiece());
		getCurrentPiece().move(dx, dy);
		board.putPiece(getCurrentPiece());
	}

	//Move current piece if it is able to, spawn a new piece if needed
	public void update() {
		if (board.canMoveDown(getCurrentPiece())) {
			movePiece(0, 1);
		} else {
			System.out.println("NEW PIECE");
			newPiece();
		}
		getCurrentPiece().printPiece();
		printBoard();
	}

	public void printBoard() {
		for (int i = 0; i < TetrisBoard.HEIGHT; i++) {
			for (int j = 0; j < TetrisBoard.WIDTH; j++) {
				System.out.print(board.getCell(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void actionPerformed(ActionEvent arg0) {
		update();
	}

	public TetrisBoard getBoard() {
		return board;
	}

	public TetrisPiece getCurrentPiece() {
		return currentPiece;
	}

	public void setCurrentPiece(TetrisPiece currentPiece) {
		this.currentPiece = currentPiece;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		this.score += score;
	}
	
	public int getLevel() {
		return level;
	}
}
