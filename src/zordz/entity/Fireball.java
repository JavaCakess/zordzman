package zordz.entity;

import java.awt.Rectangle;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class Fireball extends Entity {

	int lifeticks = 0;
	int dir;
	public float dx, dy;
	Mob shooter;
	public Fireball(Level level, float x, float y, float dx, float dy, int direction, Mob shooter) {
		super(level, x, y);
		this.dx = dx;
		this.dy = dy;
		dir = direction;
		this.shooter = shooter;
		rect = new Rectangle(Math.round(x), Math.round(y), 16, 16);
		id = 3;
	}

	public void render() {
		int xShiftPos = 0;
		if (lifeticks % 6 <= 2) xShiftPos++;
		switch (dir) {
		case Mob.UP:
			Drawer.draw(SpriteSheet.sheet, 4 + xShiftPos, 2, x, y, 16, 16, Drawer.FLIP_Y);
			break;
		case Mob.DOWN:
			Drawer.draw(SpriteSheet.sheet, 4 + xShiftPos, 2, x, y, 16, 16);
			break;
		case Mob.LEFT:
			Drawer.draw(SpriteSheet.sheet, 2 + xShiftPos, 2, x, y, 16, 16, Drawer.FLIP_X);
			break;
		case Mob.RIGHT:
			Drawer.draw(SpriteSheet.sheet, 2 + xShiftPos, 2, x, y, 16, 16);
			break;
		}
	}

	public void destroy() {
		lvl.remove(this);
		for (int i = 0; i < 4; i++) {
			lvl.add(new FlameParticle(lvl, x, y, 2));
		}
	}

	public void tick() {
		x += dx * (float)(Options.MAX_TICK_RATE / Options.TICK_RATE);
		y += dy * (float)(Options.MAX_TICK_RATE / Options.TICK_RATE);
		rect = new Rectangle(Math.round(x), Math.round(y), 16, 16);
		lifeticks++;

		for (int i = 0; i < lvl.getEntities().size(); i++) {
			Entity ent = lvl.getEntities().get(i);
			if (!(ent instanceof Mob)) continue;
			Mob m = (Mob) ent;
			if (!m.equals(shooter)) {
				if (m.intersects(this.rect)) {
					if (m.getBurnTicks() < 1) {
						m.setBurnTicks(180);
					}
					destroy();
				}
			}
		}

		if (lifeticks >= Options.TICK_RATE * 2.5f) {
			destroy();
		}
	}

}
