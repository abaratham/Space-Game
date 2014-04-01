package anandgames.spacegame.screens;

import anandgames.spacegame.pong.Pong;

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

public class PongScreen implements Screen {

	Pong pong;
	Texture spriteSheet, background;
	TextureRegion[][] sprites;
	SpriteBatch spriteBatch;
	OrthographicCamera cam;
	Mesh mesh;
	BitmapFont white;
	boolean sWasPressed, wWasPressed;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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
	public void render(float arg0) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glActiveTexture(GL10.GL_TEXTURE0);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		background.bind();
		mesh.render(GL10.GL_TRIANGLES);
		spriteBatch.begin();
		cam.update();
		checkKeys();
		spriteBatch.draw(sprites[0][6], pong.getPlayer().getX() - 10, pong
				.getPlayer().getY(), 10, 45);
		spriteBatch.draw(sprites[0][6], pong.getComputer().getX(), pong
				.getComputer().getY(), 10, 45);
		spriteBatch.draw(sprites[0][6], pong.getBall().getX(), pong.getBall()
				.getY(), 10, 10);
		white.draw(spriteBatch, "" + pong.getPlayer().getScore(), 768, 450);
		white.draw(spriteBatch, "" + pong.getComputer().getScore(), 256, 450);
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public void checkKeys() {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new PauseScreen(this));
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			pong.getPlayer().keyPressed('w');
			wWasPressed = true;
		} else {
			if (wWasPressed)
				pong.getPlayer().keyReleased('w');
			wWasPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			pong.getPlayer().keyPressed('s');
			sWasPressed = true;
		} else if (sWasPressed) {
			pong.getPlayer().keyReleased('s');
			sWasPressed = false;
		}
	}

	@Override
	public void show() {
		Pixmap pix = new Pixmap(
				Gdx.files.internal("data/Space Game/Images/Sprites.png"));
		spriteSheet = new Texture(pix);
		spriteBatch = new SpriteBatch();
		sprites = new TextureRegion(spriteSheet).split(16, 16);
		Pixmap pix2 = new Pixmap(
				Gdx.files.internal("data/Space Game/Images/PongBackground.png"));
		background = new Texture(pix2);
		white = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		mesh = new Mesh(true, 4, 6, new VertexAttribute(
				VertexAttributes.Usage.Position, 3, "attr_Position"),
				new VertexAttribute(Usage.TextureCoordinates, 2,
						"attr_texCoords"));
		mesh.setVertices(new float[] { 0f, 0, 0, 0, 1, 1024f, 0, 0, 1, 1,
				1024f, 512f, 0, 1, 0, 0, 512f, 0, 0, 0 });
		mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
		cam = new OrthographicCamera(1024, 512);
		cam.position.set(512, 256, 0);
		pong = new Pong();
	}

}
