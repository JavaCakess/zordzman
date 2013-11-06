package zordz.level.tile;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class WaterTile extends Tile {
	int currentAnim = 0;
	int anims[] = {0, 1, 2, 1};
	public WaterTile(int id) {
		super(id, 0, 0, 0x00FF00, true);
		this.ssx = 2 + currentAnim;
	}

	public void render(Level level, float x, float y) {
		Drawer.draw(SpriteSheet.sheet, 2 + anims[currentAnim], ssy, x, y, 32, 32);
	}

	public void tick(Level level, float x, float y) {
		if (level.tickCount == 60) { 
			level.tickCount = 0;
			currentAnim++;

			if (currentAnim == 4) {
				currentAnim = 0;
			}
		}
	}
}
