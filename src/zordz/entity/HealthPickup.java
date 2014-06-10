package zordz.entity;

import java.awt.Rectangle;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

/**
 * ID: 1
 */
public class HealthPickup extends Entity {
	public HealthPickup(Level level, float x, float y) {
		super(level, x, y);
		id = 1;
		rect = new Rectangle((int)this.x, (int)this.y, 16, 16);
	}

	public void render() {
		Drawer.draw(SpriteSheet.sheet, 0, 2, x, y, 16, 16);
	}

	public void tick() {
		for (int i = 0; i < lvl.getEntities().size(); i++) {
			Entity ent = lvl.getEntities().get(i);
			if (!(ent instanceof Mob)) continue;
			Mob m = (Mob) ent;
			if (m.intersects(rect)) {
				m.heal(35);
				SoundPlayer.play(Sound.health_pickup);
				lvl.remove(this);
			}
		}
		rect.x = (int)x;
		rect.y = (int)y;
	}

}
