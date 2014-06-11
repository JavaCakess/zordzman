package zordz.level.tile;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class BridgeTopTile extends Tile {

	int currentAnim;
	int anims[] = {0, 1, 2, 1};
	
	public BridgeTopTile(int id) {
		super(id, 0, 0, 0x4F4F00, true);
		this.ssx = 7 + currentAnim;
		
	}

	public void render(Level level, float x, float y) {
		Drawer.draw(SpriteSheet.sheet, 2 + anims[currentAnim], ssy, x, y, 32, 32);
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
