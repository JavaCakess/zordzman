package zordz.entity;

import java.awt.Rectangle;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

/**
 * ID: 1
 */
public class HealthPickup extends Entity {
	public HealthPickup(Level level, float x, float y) {
		super(level, x, y);
		rect = new Rectangle((int)this.x, (int)this.y, 16, 16);
	}

	public void render() {
		Drawer.draw(SpriteSheet.sheet, 0, 2, x, y, 16, 16);
	}

	public void tick() {
		for (Mob m : lvl.getMobs()) {
			if (m.intersects(rect)) {
				m.heal(35);
				m.setBurnTicks(0);
				lvl.remove(this);
			}
		}
		rect.x = (int)x;
		rect.y = (int)y;
	}

}
