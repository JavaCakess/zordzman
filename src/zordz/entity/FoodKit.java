package zordz.entity;

import java.awt.Rectangle;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

/**
 * ID: 2
 */
public class FoodKit extends Entity {

	public FoodKit(Level level, float x, float y) {
		super(level, x, y);
		id = 2;
		rect = new Rectangle(Math.round(x), Math.round(y), 16, 16);
	}

	public void render() {
		Drawer.draw(SpriteSheet.sheet, 1, 2, x, y, 16, 16);
	}

	public void tick() {
		for (int i = 0; i < lvl.getEntities().size(); i++) {
			Entity ent = lvl.getEntities().get(i);
			if (!(ent instanceof Mob)) continue;
			Mob m = (Mob) ent;
			if (m instanceof Player && m.intersects(rect)) {
				Player player = (Player) m;
				player.giveFood(2);
				SoundPlayer.play(Sound.foodkit);
				lvl.remove(this);
			}
		}
		rect.x = (int)x;
		rect.y = (int)y;
	}


}
