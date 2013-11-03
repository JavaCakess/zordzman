package zordz.entity;

import java.awt.Rectangle;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class Player extends Mob {

	public Player(Level lvl, float x, float y) {
		super(lvl, x, y);
		rect = new Rectangle((int)x, (int)y, 32, 32);
	}

	public void render() {
		int scale = 3;
		Drawer.draw(SpriteSheet.sheet, 0, 4, this.x, this.y, 8 * scale, 8 * scale);
		Drawer.draw(SpriteSheet.sheet, 1, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale);
		Drawer.draw(SpriteSheet.sheet, 1, 5, this.x + 8 * scale, this.y + 8 * scale, 8 * scale, 8 * scale);
		Drawer.draw(SpriteSheet.sheet, 0, 5, this.x, this.y + 8 * scale, 8 * scale, 8 * scale);
	}

	public void tick() {
		rect = new Rectangle((int)x, (int)y, 32, 32);
	}

}
 