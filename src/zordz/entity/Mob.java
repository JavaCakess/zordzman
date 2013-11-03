package zordz.entity;

import java.awt.Rectangle;

import zordz.level.Level;

public abstract class Mob extends Entity {

	protected int health;
	protected Rectangle rect;
	public Mob(Level lvl, float x, float y) {
		super(lvl, x, y);
	}

	public int getHealth() {
		return health;
	}
}
