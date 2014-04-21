package anandgames.spacegame;


public class Planet {

	private int x, y, radius, range, maxEffect;
	private String name;
	
	public Planet(String name, int x, int y, int radius, int effect) {
		setX(x);
		setY(y);
		setRadius(radius);
		setRange(radius * 2);
		setMaxEffect(effect);
		setName(name);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getMaxEffect() {
		return maxEffect;
	}

	public void setMaxEffect(int maxEffect) {
		this.maxEffect = maxEffect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
