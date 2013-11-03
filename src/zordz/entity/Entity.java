package zordz.entity;

import java.awt.Rectangle;

import zordz.level.Level;

public abstract class Entity {

	protected float x, y;
	protected Level lvl;
	
	public Entity(Level level, float x, float y) {
		this.x = x;
		this.y = y;
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

	
	public abstract void render();
	public abstract void tick();
}
