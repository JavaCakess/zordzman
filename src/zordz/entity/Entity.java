package zordz.entity;

import java.awt.Rectangle;

import zordz.level.Level;

public abstract class Entity {

	protected float x, y;
	protected Level lvl;
	public int id;
	public Rectangle rect = new Rectangle();
	public Entity(Level level, float x, float y) {
		this.x = x;
		this.y = y;
		rect.x = (int)x;
		rect.y = (int)y;
		this.lvl = level;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public static Entity createEntity(Level l, int id) {
		switch (id) {
		case 1:
			return new HealthPickup(l, 0, 0);
		}
		return new HealthPickup(l, 0, 0);
	}
	
	public static Entity parseEntity(Level l, String s) {
		int id = Integer.parseInt(s.split(";")[0]);
		
		Entity e = createEntity(l, id);
		e.id = id;
		e.x = Float.parseFloat(s.split(";")[1]);
		e.y = Float.parseFloat(s.split(";")[2]);
		return e;
	}
	
	public abstract void render();
	public abstract void tick();
}
