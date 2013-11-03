package zordz.entity;

import cjaf.tools.NewGLHandler;
import zordz.gfx.Text;
import zordz.level.Level;

public class TextParticle extends Entity {

	public static final int MAX_TICKS = 30;
	private int ticksToLive;
	private String str;
	private float[] col;
	
	public TextParticle(Level level, float x, float y, String str, float[] col) {
		super(level, x, y);
		ticksToLive = MAX_TICKS;
		this.str = str;
		this.col = col;
	}

	public void render() {
		int sz = 16;
		NewGLHandler.setCurrentColor(new float[]{col[0], col[1], col[2], ticksToLive * (1.0f / MAX_TICKS)}, true);
		Text.render(str, x, y, sz, sz);
	}

	public void tick() {
		y--;
		
		ticksToLive--;
		if (ticksToLive == 0) lvl.remove(this);
	}

}
