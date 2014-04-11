package anandgames.spacegame;

import java.util.ArrayList;
import java.util.Random;

import anandgames.spacegame.entities.Bullet;
import anandgames.spacegame.entities.Enemy;
import anandgames.spacegame.entities.PlayerShip;
import anandgames.spacegame.entities.ShootingEnemy;
import anandgames.spacegame.pickups.FlameThrower;
import anandgames.spacegame.pickups.Weapon;
import anandgames.spacegame.screens.GameScreen;
import anandgames.spacegame.tweens.ShotgunTweenAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Board {

	private PlayerShip ship;
	private ArrayList<Enemy> enemies;
	private GameScreen game;
	private int width = 8192;
	private ArrayList<Planet> planets;
	private int height = 8192;
	private int counter = 0, currentPhase = 0, currentWave = 0;
	private boolean inGame = true;
	private ArrayList<Weapon> weaponList;
	private TweenManager tManager;

	public Board(GameScreen gs) {
		game = gs;
		ship = new PlayerShip(this);
		initEnemies();
		weaponList = new ArrayList<Weapon>();
		initTweenManager();
		// for testing
		// spawnWeapon();
	}

	// Set up the Tween Manager
	public void initTweenManager() {
		tManager = new TweenManager();
		Tween.registerAccessor(Weapon.class, new ShotgunTweenAccessor());
	}

	// Initialize enemies
	public void initEnemies() {
		inGame = true;
		currentPhase = 0;
		currentWave = 0;
		enemies = new ArrayList<Enemy>();
		// initPlanets();
		newWave();
	}

	// Add Planets to the Board
	public void initPlanets() {
		planets = new ArrayList<Planet>();
		// Frogger
		planets.add(new Planet(2048 - 189, 2048 - 189, 60, null));
		// Phoenix
		planets.add(new Planet(2048 - 1269, 2048 - 765, 25, null));
		// Space Invaders
		planets.add(new Planet(2048 - 1630, 2048 - 181, 25, null));
		// Pong
		planets.add(new Planet(2048 - 906, 2048 - 1285, 25, null));
		// Tetris
		planets.add(new Planet(2048 - 541, 2048 - 1603, 50, null));
	}

	// Check if the ship is over a planet
	public Planet isOnPlanet() {
		for (int i = 0; i < planets.size(); i++) {
			Planet p = planets.get(i);
			int dist = (int) Math.sqrt((Math.pow(
					ship.getPosition().x - p.getX(), 2) + (Math.pow(
					ship.getPosition().y - p.getY(), 2))));
			if (dist < ship.getRadius() + p.getRadius())
				return p;
		}
		return null;
	}

	// Generate a new wave of enemies
	public void newWave() {
		Random r = new Random();
		for (int i = 0; i < 5 * currentWave; i++) {
			// Start spawning shooting enemies at wave 5
			if (currentWave >= 5) {
				double prob = Math.random();
				if (prob >= .9 * currentWave)
					enemies.add(new ShootingEnemy(new Vector2(r
							.nextInt(getWidth()), r.nextInt(getHeight())), this));
				else
					enemies.add(new Enemy(new Vector2(r.nextInt(getWidth()), r
							.nextInt(getHeight())), this));
			}
		}
	}

	// Reset the game to the first wave of enemies and ship starting position
	public void reset() {
		ship = new PlayerShip(this);
		initEnemies();
	}

	// Spawn a new random Weapon at a random location
	public void spawnWeapon() {
		// TODO: use to weight weapon spawns
		double prob = Math.random();
		Weapon wep = new FlameThrower(this, ship.getPosition().x,
				ship.getPosition().y);
		Tween.to(wep, 0, 4.0f).target(360).repeat(-1, 0f).start(tManager);
		weaponList.add(wep);

	}

	// Move and re-orient all entities, spawn a new weapon randomly, check for
	// collisions, and update enemies
	// if needed
	public void update() {
		tManager.update(.032f);
		double f = Math.random();
		// TODO: pick the probability of a weapon spawn
		if (f <= 0)
			spawnWeapon();
		if (enemies.size() == 0) {
			currentWave++;
			newWave();
			ship.getBullets().clear();

		}
		checkCollisions();

		// Move and re-orient all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (e.isVisible()) {
				e.move();
			}
		}

		// Move all bullets
		for (int i = 0; i < ship.getBullets().size(); i++) {
			Bullet b = ship.getBullets().get(i);
			b.move();
			if (b.getPosition().x > ship.getPosition().x
					+ Gdx.graphics.getWidth() / 2
					|| b.getPosition().x < ship.getPosition().x
							- Gdx.graphics.getWidth() / 2
					|| b.getPosition().y > ship.getPosition().y
							+ Gdx.graphics.getHeight() / 2
					|| b.getPosition().y < ship.getPosition().y
							- Gdx.graphics.getHeight() / 2)
				ship.getBullets().remove(b);
		}

		// Move and re-orient the ship
		ship.move();
		ship.reOrient();

		int limit;
		if (ship.getWeapon() == null)
			limit = 12;
		else {
			limit = ship.getWeapon().getLimiter();
		}
		System.out.println(counter);
		// Fire if the mouse is held
		if (ship.isMouseHeld() && counter == limit)
			ship.fire();
		else if (!ship.isMouseHeld())
			counter = 0;

		// Create delay for MouseHeld firing
		if (counter == limit)
			counter = 0;
		else
			counter++;
	}

	public PlayerShip getShip() {
		return ship;
	}

	// Return the current wave of enemies
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	// Check for player-enemy and enemy-bullet collisions
	public void checkCollisions() {
		ArrayList<Bullet> bullets = ship.getBullets();

		// Check player-weapon collisions
		for (Weapon w : weaponList) {
			float deltaX = (ship.getPosition().x + ship.getRadius())
					- (w.getX() + w.getRadius());
			float deltaY = (ship.getPosition().y + ship.getRadius())
					- (w.getY() - w.getRadius());
			float shipDist = (float) Math.sqrt(Math.pow(deltaX, 2)
					+ Math.pow(deltaY, 2));

			// Ship picked up the weapon
			if (ship.getRadius() + w.getRadius() >= shipDist) {
				weaponList.remove(w);
				ship.setWeapon(w);
				break;
			}

		}

		for (int i = 0; i < enemies.size(); i++) {
			// Check player-enemy collisions
			Enemy e = enemies.get(i);
			float deltaX = (ship.getPosition().x + ship.getRadius())
					- (e.getPosition().x + e.getRadius());

			float deltaY = (ship.getPosition().y - ship.getRadius())
					- (e.getPosition().y - e.getRadius());

			float shipDist = (float) Math.sqrt(Math.pow(deltaX, 2)
					+ Math.pow(deltaY, 2));

			if (ship.getRadius() + e.getRadius() >= shipDist) {
				// The ship has a shield around it
				if (ship.isShielded())
					ship.setShielded(false);
				else {
					ship.setVisible(false);
					inGame = false;
				}
			}
			// Check bullet-enemy collisions for each bullet
			for (int j = 0; j < bullets.size(); j++) {
				Bullet b = bullets.get(j);
				float _deltaX = (b.getPosition().x + b.getRadius())
						- (e.getPosition().x + e.getRadius());

				float _deltaY = (b.getPosition().y - b.getRadius())
						- (e.getPosition().y - e.getRadius());

				float bDist = (float) Math.sqrt(Math.pow(_deltaX, 2)
						+ Math.pow(_deltaY, 2));

				if (b.getRadius() + e.getRadius() >= bDist) {
					e.setVisible(false);
					b.setVisible(false);
					game.addExplosion((int) e.getPosition().x,
							(int) e.getPosition().y);
					game.getSound("Explosion").play(.7f);
					bullets.remove(j);
					ship.setScore(ship.getScore() + 10);
					enemies.remove(i);
					if (enemies.size() == 0)
						return;
				}

			}
		}

	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public int getWave() {
		return currentPhase;
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

	public ArrayList<Weapon> getWeaponList() {
		return weaponList;
	}

	public void setWeaponList(ArrayList<Weapon> weaponList) {
		this.weaponList = weaponList;
	}

	public TweenManager getTManager() {
		return tManager;
	}
}
