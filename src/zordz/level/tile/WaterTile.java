package zordz.level.tile;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class WaterTile extends Tile {
	int currentAnim = 0;
	int tick = 0;

	public WaterTile(int id) {
		super(id, 0, 0, true);
		this.ssx = 2 + currentAnim;
	}

	public void render(Level level, float x, float y) {
		Drawer.draw(SpriteSheet.sheet, 2 + currentAnim, ssy, x, y, 32, 32);
	}

	public void tick(Level level, float x, float y) {
		if (tick == 60) { 
			tick = 0;
			currentAnim++;

			if (currentAnim == 3) {
				currentAnim = 0;
			}
		}
		tick++;
	}
}
