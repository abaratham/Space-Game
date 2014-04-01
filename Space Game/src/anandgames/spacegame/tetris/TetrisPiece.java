package anandgames.spacegame.tetris;

public class TetrisPiece {
	
	private int row, col, width, height;
	private int[][] config;
	
	public TetrisPiece(int[][] pieceConfig) {
		config = pieceConfig;
		row = 0;
		col = 4;
		width = config[0].length;
		height = config.length;
	}
	
	public void move(int dx, int dy) {
		row += dy;
		col += dx;
	}
	
	public void rotate() {
		int temp = width;
		width = height;
		height = temp;
		int colNew = width - 1;
		int rowNew = 0;
		int[][] tempC = config.clone();
		int[][] newConfig = new int[height][width];
		for (int rowOld = 0; rowOld < width; rowOld++) {
			for (int colOld = 0; colOld < height; colOld++) {
				newConfig[rowNew][colNew] = tempC[rowOld][colOld];
				rowNew++;
			}
			colNew--;
			rowNew = 0;
		}
		config = newConfig;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
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

	public int[][] getConfig() {
		return config;
	}

	public void setConfig(int[][] config) {
		this.config = config;
	}
	
	public void printPiece() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(config[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		TetrisPiece t = new TetrisPiece(new int[][]{{1,0},{1,0},{1,1}});
		t.printPiece();
		t.rotate();
		t.printPiece();
		t.rotate();
		t.printPiece();
		t.rotate();
		t.printPiece();
		t.rotate();
		t.printPiece();
		
	}

}
