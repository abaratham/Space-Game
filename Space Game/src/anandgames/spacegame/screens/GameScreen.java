package anandgames.spacegame.screens;

import java.awt.Point;
import java.util.ArrayList;

import anandgames.spacegame.Board;
import anandgames.spacegame.animations.ExplosionAnimation;
import anandgames.spacegame.entities.Asteroid;
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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {
	private SpriteBatch spriteBatch;
	private Texture spriteSheet, shipSheet;
	private OrthographicCamera cam;
	private TextureRegion[][] sprites;
	private TextureRegion[] shipFrames;
	private Board board;
	private Animation shipAnimation;
	private TextureRegion currentShipFrame;
	private BitmapFont font, messageFont;
	private PlayerShip ship;
	private boolean aWasPressed, sWasPressed, dWasPressed, wWasPressed;
	private int frameCount, mx, my, mCounter;
	private float stateTime;
	private Sound fire, explosion, background;
	private ArrayList<ExplosionAnimation> explosionAnimations;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private long backgroundID;
	private String message;
	private AssetManager manager;

	public GameScreen(AssetManager manager) {
		super();
		this.manager = manager;
		// Initialize the ship's flame animation
		initAnimation();

		// Initialize fonts
		font = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		font.setScale(.8f);

		messageFont = new BitmapFont(
				Gdx.files.internal("data/Space Game/Fonts/White.fnt"), false);
		Color c = messageFont.getColor();
		// System.out.println(c.a);
		messageFont.setColor(c.r, c.g, c.b, 1);

		// Initialize Tween Manager
		// tManager = new TweenManager();
		// Tween.registerAccessor(BitmapFont.class, new MessageTweenAccessor());

		// Set up the sprite sheet
		Pixmap pix = manager.get("data/Space Game/Images/Sprites.png");
		spriteSheet = new Texture(pix);
		spriteBatch = new SpriteBatch();
		sprites = new TextureRegion(spriteSheet).split(16, 16);

		// Initialize game components
		board = new Board(this);
		ship = board.getShip();

		// Frame count used for update delay
		frameCount = 0;

		// Set up Animation and Sound for Enemy explosions
		explosionAnimations = new ArrayList<ExplosionAnimation>();
		explosion = manager.get("data/Space Game/Sounds/Explosion.wav");

		tiledMap = new TmxMapLoader().load("data/Space Game/Other/StarMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1f);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background = manager.get("data/Space Game/Sounds/loop1.mp3");
		// backgroundID = background.loop();
	}

	// Initialize the ship's flame animation
	public void initAnimation() {
		shipSheet = manager.get("data/Space Game/Images/ShipFrames.png");
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
			Gdx.input.setInputProcessor(new MyInputProcessor());
			PlayerShip ship = board.getShip();
			checkKeys();
			cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
			cam.update();
			mapRenderer.setView(cam);
			mapRenderer.render();
			spriteBatch.setProjectionMatrix(cam.combined);
			spriteBatch.begin();

			drawBullets();
			drawEnemies();
			drawShip();
			drawInfo();
			drawExplosions();
			drawWeapons();
			drawAsteroids();
			drawInfo();
			if (message != null)
				drawMessage(delta);

			spriteBatch.end();
		} else {
			background.stop(backgroundID);
			((Game) (Gdx.app.getApplicationListener()))
					.setScreen(new GameOverScreen(ship.getScore()));

		}
	}

	public void drawAsteroids() {
		for (Asteroid x : board.getAsteroids()) {
			spriteBatch.draw(sprites[x.getSpriteKey().x][x.getSpriteKey().y],
					(float) x.getPosition().x, (float) x.getPosition().y, 30f,
					30f, 60f, 60f, 2, 2,
					(float) Math.toDegrees(x.getOrientation()));
		}
	}

	public void drawMessage(float delta) {
		mCounter++;
		my = (int) board.getShip().getPosition().y - 300;
		mx = (int) board.getShip().getPosition().x
				- (10 * (message.length() / 2));
		messageFont.draw(spriteBatch, message, mx, my);
		System.out.println(mCounter);
		if (mCounter >= (int) (3 / delta)) {
			message = null;
			mCounter = 0;
		}
	}

	public void showMessage(String message) {
		this.message = message;
		// Tween.to(messageFont, 0, 3f).target(.99f).start(tManager);
		// message = null;
	}

	public void drawWeapons() {
		for (Weapon x : board.getWeaponList()) {
			spriteBatch.draw(sprites[x.getSpriteKey().x][x.getSpriteKey().y],
					(float) x.getX(), (float) x.getY(), 16f, 16f, 32f, 32f, 1f,
					1f, (float) x.getOrientation());
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
				ship.getPosition().x + 400, ship.getPosition().y + 360);
		font.draw(spriteBatch, "Special Ammo:"
				+ board.getShip().getCurrentAmmo(), ship.getPosition().x + 400,
				ship.getPosition().y + 310);
	}

	public void drawShip() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentShipFrame = shipAnimation.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentShipFrame, (float) board.getShip()
				.getPosition().x, (float) board.getShip().getPosition().y, 10f,
				10f, 20f, 20f, 2, 2, (float) Math.toDegrees(ship
						.getOrientation()));

		// TODO: draw a shield sprite at 1,12 in Sprites.png
		if (board.getShip().isShielded())
			spriteBatch.draw(sprites[1][12], (float) board.getShip()
					.getPosition().x, (float) board.getShip().getPosition().y,
					4f, 4f, 8f, 8f, 1, 1, (float) Math.toDegrees(ship
							.getOrientation()));
	}

	public void drawEnemies() {
		for (int i = 0; i < board.getEnemies().size(); i++) {
			Enemy e = board.getEnemies().get(i);
			spriteBatch.draw(sprites[e.getSpriteKey().x][e.getSpriteKey().y],
					(float) e.getPosition().x, (float) e.getPosition().y, 10f,
					10f, 20f, 20f, 2, 2,
					(float) Math.toDegrees(e.getOrientation()));
			if (e instanceof ShootingEnemy) {
				for (int j = 0; j < ((ShootingEnemy) (e)).getBullets().size(); j++) {
					spriteBatch.draw(sprites[0][2],
							(float) ((ShootingEnemy) (e)).getBullets().get(j)
									.getPosition().x,
							(float) ((ShootingEnemy) (e)).getBullets().get(j)
									.getPosition().y, 1.5f, 1.5f, 3f, 3f, 1f,
							1f, 0f);
				}
			}
		}
	}

	public void drawBullets() {
		int sz;
		if (ship.getWeapon() != null)
			sz = ship.getWeapon().getAmmoRadius();
		else
			sz = 7;
		for (int i = 0; i < ship.getBullets().size(); i++) {
			Point p;
			if (ship.getWeapon() == null)
				p = new Point(0, 2);
			else
				p = ship.getWeapon().getBulletKey();
			spriteBatch.draw(sprites[p.x][p.y], (float) ship.getBullets()
					.get(i).getPosition().x, (float) ship.getBullets().get(i)
					.getPosition().y, sz, sz, 2 * sz, 2 * sz, 1f, 1f, 0f);
		}
	}

	public void checkKeys() {
		PlayerShip ship = board.getShip();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new PauseScreen(this));
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

		// TESTING
		if (Gdx.input.isKeyPressed(Keys.C)) {
			if (board.collisions)
				board.collisions = false;
			else
				board.collisions = true;
			// spriteBatch.begin();
			showMessage("Collisions: " + board.collisions);
			// spriteBatch.end();
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			ship.setMaxSpeed(ship.getMaxSpeed() - 1);
			for (Enemy x : board.getEnemies()) {
				x.setMaxSpeed(x.getMaxSpeed() - 1);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			ship.setMaxSpeed(ship.getMaxSpeed() + 1);
			for (Enemy x : board.getEnemies()) {
				x.setMaxSpeed(x.getMaxSpeed() + 1);
			}
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
		backgroundID = background.loop();
	}

	@Override
	public void hide() {
		background.stop(backgroundID);
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
