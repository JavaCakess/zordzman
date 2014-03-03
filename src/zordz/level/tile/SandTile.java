package zordz.level.tile;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class SandTile extends Tile {

	public SandTile(int id, int ssx, int ssy) {
		super(id, ssx, ssy, 0xFFFF00, false);
	}
	
	public void render(Level level, float x, float y) {
		Drawer.draw(SpriteSheet.sheet, ssx, ssy, x, y, 32, 32);
	}

	public void tick(Level level, float x, float y) {

	}

}