package anandgames.spacegame.tetris;

public class TetrisBoard {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 18;
	private int[][] board = new int[HEIGHT][WIDTH];

	public TetrisBoard() {
	}

	public int getCell(int row, int col) {
		return board[row][col];
	}

	public void setCell(int row, int col, int num) {
		board[row][col] = num;
	}

	public boolean isValid(int row, int col) {
		if (row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH)
			return true;
		return false;
	}

	//Check if the given piece can fit in the given coordinates
	public boolean validPieceLoc(TetrisPiece p, int row, int col) {
		for (int i = row; i < row + p.getHeight(); i++) {
			for (int j = col; j < col + p.getWidth(); j++) {
				if (!isValid(i, j))
					return false;
			}
		}
		return true;
	}

	public int[] getRow(int row) {
		return board[row];
	}

	public void setRow(int[] row, int rowNum) {
		for (int i = 0; i < row.length; i++) {
			board[rowNum][i] = row[i];
		}
	}

	//Shift all rows down starting at given row
	public void clearRow(int row) {
		for (int i = row; i > 0; i--) {
			setRow(getRow(i - 1), i);
		}
	}

	//Not sure if I actually use this...
	public void shiftRows(int offset, int start) {
		for (int i = start; i > offset; i--) {
			setRow(getRow(i - offset), i);
		}
		for (int i = 0; i < offset; i++) {
			setRow(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, i);
		}
	}

	//Check if given Piece can move down
	public boolean canMoveDown(TetrisPiece p) {
		if (!validPieceLoc(p, p.getRow() + 1, p.getCol()))
			return false;
		else {
			int[][] config = p.getConfig();
			for (int i = 0; i < config.length; i++) {
				for (int j = 0; j < config[0].length; j++) {
					if (config[i][j] == 1
							&& board[p.getRow() + i + 1][p.getCol() + j] == 1) {
						if (i != p.getHeight() - 1) {
							if (config[i + 1][j] == 0) {
								return false;
							}
						} else
							return false;

					}

				}
			}
		}
		return true;
	}

	//Check if the given Piece can move left
	public boolean canMoveLeft(TetrisPiece p) {
		if (!validPieceLoc(p, p.getRow(), p.getCol() - 1))
			return false;
		int boardLeftRow = p.getCol();
		for (int i = 0; i < p.getHeight(); i++) {
			if ((board[i + p.getRow()][boardLeftRow] == 1 && p.getConfig()[i][0] == 0)
					|| (p.getConfig()[i][0] == 1 && board[i + p.getRow()][boardLeftRow - 1] == 1))
				return false;
		}
		return true;
	}

	//Check if the given Piece can move right
	public boolean canMoveRight(TetrisPiece p) {
		if (!validPieceLoc(p, p.getRow(), p.getCol() + 1))
			return false;
		int boardRightRow = p.getCol() + p.getWidth() - 1;
		for (int i = 0; i < p.getHeight(); i++) {
			if ((board[i + p.getRow()][boardRightRow] == 1 && p.getConfig()[i][p
					.getWidth() - 1] == 0)
					|| (p.getConfig()[i][p.getWidth() - 1] == 1 && board[i
							+ p.getRow()][boardRightRow + 1] == 1))
				return false;
		}
		return true;
	}

	//Remove a piece from the Board
	public void clearPiece(TetrisPiece p) {
		int pRow = 0, pCol = 0;
		for (int i = p.getRow(); i < p.getRow() + p.getHeight(); i++) {
			for (int j = p.getCol(); j < p.getCol() + p.getWidth(); j++) {
				if (p.getConfig()[pRow][pCol] == 1)
					setCell(i, j, 0);
				pCol++;
			}
			pRow++;
			pCol = 0;
		}
	}

	//Put given Piece on the Board
	public void putPiece(TetrisPiece p) {
		int pRow = 0, pCol = 0;
		for (int i = p.getRow(); i < p.getRow() + p.getHeight(); i++) {
			for (int j = p.getCol(); j < p.getCol() + p.getWidth(); j++) {
				if (p.getConfig()[pRow][pCol] == 1)
					setCell(i, j, 1);
				pCol++;
			}
			pRow++;
			pCol = 0;
		}
	}
}
