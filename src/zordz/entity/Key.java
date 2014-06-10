package zordz.entity;

import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class Key extends Entity {
	
	int color = 0; // 0 = Red
	int ticks = 0;
	
	public Key(Level level, float x, float y, int color) {
		super(level, x, y);
		this.color = color;
	}

	public void render() {
		Drawer.draw(SpriteSheet.sheet, color, 3, x, y, 16, 16);
	}

	public void tick() {
		ticks++;
		if (ticks % 60 < 30) y += 0.15; else y -= 0.15;
	}

}
