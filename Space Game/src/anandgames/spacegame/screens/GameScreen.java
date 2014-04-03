package anandgames.spacegame.screens;

import java.awt.Point;
import java.util.ArrayList;

import anandgames.spacegame.Board;
import anandgames.spacegame.animations.ExplosionAnimation;
import anandgames.spacegame.entities.Enemy;
import anandgames.spacegame.entities.PlayerShip;
import anandgames.spacegame.entities.ShootingEnemy;
import anandgames.spacegame.pickups.Weapon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	private SpriteBatch spriteBatch;
	private Texture spriteSheet, shipSheet;
	private Texture spaceMap;
	private OrthographicCamera cam;
	private Mesh mesh;
	private TextureRegion[][] sprites;
	private TextureRegion[] shipFrames;
	private Board board;
	private Animation shipAnimation;
	private TextureRegion currentShipFrame;
	private BitmapFont font;
	private PlayerShip ship;
	private boolean aWasPressed, sWasPressed, dWasPressed, wWasPressed;
	private int frameCount;
	private float stateTime;
	private Sound fire, explosion;
	private ArrayList<ExplosionAnimation> explosionAnimations;

	public GameScreen() {
		super();
		// Initialize the ship's flame animation
		initAnimation();

		// Initialize font used for score
		font = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		font.setScale(.2f);

		// Set up the sprite sheet
		Pixmap pix = new Pixmap(
				Gdx.files.internal("data/Space Game/Images/Sprites.png"));
		spriteSheet = new Texture(pix);
		spriteBatch = new SpriteBatch();
		sprites = new TextureRegion(spriteSheet).split(16, 16);

		// Initialize game components
		board = new Board(this);
		ship = board.getShip();

		// Set up background map and camera
		mesh = new Mesh(true, 4, 6, new VertexAttribute(
				VertexAttributes.Usage.Position, 3, "attr_Position"),
				new VertexAttribute(Usage.TextureCoordinates, 2,
						"attr_texCoords"));
		spaceMap = new Texture(
				Gdx.files.internal("data/Space Game/Images/SpaceMap.png"));
		mesh.setVertices(new float[] { 0f, 0, 0, 0, 1, 2048f, 0, 0, 1, 1,
				2048f, 2048f, 0, 1, 0, 0, 2048f, 0, 0, 0 });
		mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
		cam = new OrthographicCamera(1280, 720);
		cam.position.set(board.getWidth() / 2, board.getHeight() / 2, 0);
		cam.zoom = .25f;

		// Frame count used for update delay
		frameCount = 0;

		// Set up Animation and Sound for Enemy explosions
		explosionAnimations = new ArrayList<ExplosionAnimation>();
		explosion = Gdx.audio.newSound(Gdx.files
				.internal("data/Space Game/Sounds/Explosion.wav"));
	}

	// Initialize the ship's flame animation
	public void initAnimation() {
		shipSheet = new Texture(
				Gdx.files.internal("data/Space Game/Images/ShipFrames.png"));
		TextureRegion[][] tmp = new TextureRegion(shipSheet).split(16, 16);
		shipFrames = new TextureRegion[tmp.length * tmp[0].length];
		int count = 0;
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++) {
				shipFrames[count++] = tmp[i][j];
			}
		}
		shipAnimation = new Animation(.1f, shipFrames);
		stateTime = 0;
	}

	@Override
	public void render(float delta) {
		if (frameCount != 1)
			frameCount++;
		else if (frameCount == 1) {
			board.update();
			frameCount = 0;
		}
		if (board.isInGame()) {
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glActiveTexture(GL10.GL_TEXTURE0);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			spaceMap.bind();
			mesh.render(GL10.GL_TRIANGLES);
			Gdx.input.setInputProcessor(new MyInputProcessor());
			PlayerShip ship = board.getShip();
			checkKeys();
			spriteBatch.begin();
			// int camx, camy;
			// if (ship.getX() >= 640 && ship.getX() <= board.getWidth() - 640)
			// camx = ship.getX();
			// else if (ship.getX() < 640)
			// camx = 640;
			// else
			// camx = board.getWidth() - 640;
			// if (ship.getY() >= 360 && ship.getX() <= board.getHeight() - 360)
			// camy = ship.getY();
			// else if (ship.getY() < 360)
			// camy = 360;
			// else
			// camy = board.getHeight() - 360;
			cam.position.set(ship.getX(), ship.getY(), 0);
			cam.update();
			spriteBatch.setProjectionMatrix(cam.combined);

			drawBullets();
			drawEnemies();
			drawShip();
			drawInfo();
			drawExplosions();
			drawWeapons();

			spriteBatch.end();
		} else {
			((Game) (Gdx.app.getApplicationListener()))
					.setScreen(new GameOverScreen());
		}

	}

	public void drawWeapons() {
		for (Weapon x : board.getWeaponList()) {
			spriteBatch.draw(sprites[x.getSpriteKey().x][x.getSpriteKey().y],
					(float) x.getX(), (float) x.getY(), 6f, 6f, 12f, 12f, .5f,
					.5f, (float) x.getOrientation());
		}
	}

	public void drawExplosions() {
		for (int i = 0; i < explosionAnimations.size(); i++) {
			explosionAnimations.get(i).drawCurrentFrame(spriteBatch, stateTime);
			if (explosionAnimations.get(i).getAnimation()
					.isAnimationFinished(stateTime))
				explosionAnimations.remove(i);
		}
	}

	public void drawInfo() {
		font.draw(spriteBatch, "Score: " + board.getShip().getScore(),
				ship.getX() + 100, ship.getY() + 90);
		font.draw(spriteBatch, "Special Ammo:"
				+ board.getShip().getCurrentAmmo(), ship.getX() + 100,
				ship.getY() + 85);
	}

	public void drawShip() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentShipFrame = shipAnimation.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentShipFrame, (float) board.getShip().getX(),
				(float) board.getShip().getY(), 4f, 4f, 8f, 8f, 1, 1,
				(float) Math.toDegrees(ship.getOrientation()));

		// TODO: draw a shield sprite at 1,12 in Sprites.png
		if (board.getShip().isShielded())
			spriteBatch.draw(sprites[1][12], (float) board.getShip().getX(),
					(float) board.getShip().getY(), 4f, 4f, 8f, 8f, 1, 1,
					(float) Math.toDegrees(ship.getOrientation()));
	}

	public void drawEnemies() {
		for (int i = 0; i < board.getEnemies().size(); i++) {
			Enemy e = board.getEnemies().get(i);
			spriteBatch.draw(sprites[e.getSpriteKey().x][e.getSpriteKey().y], (float) e.getX(), (float) e.getY(),
					4f, 4f, 8f, 8f, 1, 1,
					(float) Math.toDegrees(e.getOrientation()));
			if (e instanceof ShootingEnemy) {
				for (int j = 0; j < ((ShootingEnemy) (e)).getBullets().size(); j++) {
					spriteBatch.draw(sprites[0][2],
							(float) ((ShootingEnemy) (e)).getBullets().get(j)
									.getX(), (float) ((ShootingEnemy) (e))
									.getBullets().get(j).getY(), 1.5f, 1.5f,
							3f, 3f, 1f, 1f, 0f);
				}
			}
		}
	}

	public void drawBullets() {
		for (int i = 0; i < ship.getBullets().size(); i++) {
			Point p;
			if (ship.getWeapon() == null)
				p = new Point(0, 2);
			else
				p = ship.getWeapon().getBulletKey();
			spriteBatch.draw(sprites[p.x][p.y], (float) ship.getBullets()
					.get(i).getX(), (float) ship.getBullets().get(i).getY(),
					1.5f, 1.5f, 3f, 3f, 1f, 1f, 0f);
		}
	}

	public void checkKeys() {
		PlayerShip ship = board.getShip();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new PauseScreen(this));
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (board.isOnPlanet() != null)
				((Game) Gdx.app.getApplicationListener()).setScreen(board
						.isOnPlanet().getScreen());
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			ship.keyPressed('w');
			wWasPressed = true;
		} else {
			if (wWasPressed)
				ship.keyReleased('w');
			wWasPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			ship.keyPressed('a');
			aWasPressed = true;
		} else if (aWasPressed) {
			ship.keyReleased('a');
			aWasPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			ship.keyPressed('d');
			dWasPressed = true;
		} else if (dWasPressed) {
			ship.keyReleased('d');
			dWasPressed = false;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			ship.keyPressed('s');
			sWasPressed = true;
		} else if (sWasPressed) {
			ship.keyReleased('s');
			sWasPressed = false;
		}
	}

	public void addExplosion(int x, int y) {
		explosionAnimations.add(new ExplosionAnimation(x, y));
	}

	public SpriteBatch getBatch() {
		return spriteBatch;
	}

	public Sound getSound(String sound) {
		switch (sound) {
		case "Fire":
			return fire;
		case "Explosion":
			return explosion;
		default:
			return null;
		}
	}

	private class MyInputProcessor implements InputProcessor {
		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {
			if (button == Input.Buttons.LEFT) {
				board.getShip().mousePressed();
				return true;
			}
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			board.getShip().mouseReleased();
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
