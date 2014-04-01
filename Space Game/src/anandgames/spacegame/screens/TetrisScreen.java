package anandgames.spacegame.screens;

import anandgames.spacegame.tetris.Tetris;
import anandgames.spacegame.tetris.TetrisBoard;
import anandgames.spacegame.tetris.TetrisPiece;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TetrisScreen implements Screen {

	private Tetris t;
	SpriteBatch spriteBatch;
	Texture spriteSheet;
	TextureRegion[][] sprites;
	Texture background;
	Table table;
	OrthographicCamera cam;
	private Mesh mesh;
	float delay;
	boolean downPressed;
	BitmapFont white;

	@Override
	public void render(float delta) {
		delay += Gdx.graphics.getDeltaTime();
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glActiveTexture(GL10.GL_TEXTURE0);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		background.bind();
		mesh.render(GL10.GL_TRIANGLES);
		spriteBatch.begin();
		if (delay >= .079)
			checkKeys();
		TetrisBoard board = t.getBoard();
		for (int i = 0; i < TetrisBoard.HEIGHT; i++) {
			for (int j = 0; j < TetrisBoard.WIDTH; j++) {
				if (board.getCell(i, j) == 1) {
					int drawX = 0, drawY = 0;
					drawX = j * 25 + 374 + (j * 3);
					drawY = ((19 - i) * 25) - ((i - 1) * 3) + 5;
					spriteBatch.draw(sprites[0][4], drawX, drawY, 25, 25);
				}
			}
		}
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		 white.draw(spriteBatch, "Score: " + t.getScore(), 100, 400);
		 white.draw(spriteBatch, "Level: " + t.getLevel(), 100, 300);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void checkKeys() {
		delay = 0;
		TetrisPiece piece = t.getCurrentPiece();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new PauseScreen(this));
		// Rotate the piece if the rotated piece will fit in the current
		// location
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			TetrisPiece tmp = new TetrisPiece(piece.getConfig());
			tmp.rotate();
			if (t.getBoard().validPieceLoc(tmp, piece.getRow(), piece.getCol())) {
				t.getBoard().clearPiece(t.getCurrentPiece());
				piece.rotate();
				t.getBoard().putPiece(t.getCurrentPiece());
			}
		}
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (t.getBoard().canMoveLeft(piece)) {
				t.movePiece(-1, 0);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (t.getBoard().canMoveRight(piece))
				t.movePiece(1, 0);

		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			t.update();
		}
	}

	@Override
	public void show() {
		delay = 0;
		t = new Tetris();
		Pixmap pix = new Pixmap(
				Gdx.files.internal("data/Space Game/Images/Sprites.png"));
		spriteSheet = new Texture(pix);
		spriteBatch = new SpriteBatch();
		sprites = new TextureRegion(spriteSheet).split(16, 16);
		white = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		Pixmap pix2 = new Pixmap(
				Gdx.files
						.internal("data/Space Game/Images/TetrisBackground.png"));
		background = new Texture(pix2);
		mesh = new Mesh(true, 4, 6, new VertexAttribute(
				VertexAttributes.Usage.Position, 3, "attr_Position"),
				new VertexAttribute(Usage.TextureCoordinates, 2,
						"attr_texCoords"));
		mesh.setVertices(new float[] { 0f, 0, 0, 0, 1, 1024f, 0, 0, 1, 1,
				1024f, 512f, 0, 1, 0, 0, 512f, 0, 0, 0 });
		mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }); // #5
		cam = new OrthographicCamera(1024, 512); // #6
		cam.position.set(512, 256, 0); // #7

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
