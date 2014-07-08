package zordz.level.tile;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class BridgeBottomTile extends Tile {
	int currentAnim = 0;
	int anims[] = {0, 1, 2, 1};
	public BridgeBottomTile(int id) {
		super(id, 0, 0, 0x4F5F00, false);
		this.ssx = 11 + currentAnim;
	}

	public void render(Level level, float x, float y) {
		Drawer.draw(SpriteSheet.sheet, 11 + anims[currentAnim], ssy, x, y, 32, 32);
	}

	public void tick(Level level, float x, float y) {
		if (level.tickCount == Options.TICK_RATE) { 
			level.tickCount = 0;
			currentAnim++;

			if (currentAnim == 4) {
				currentAnim = 0;
			}
		}
	}

}
