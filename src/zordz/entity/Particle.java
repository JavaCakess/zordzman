package zordz.entity;

import java.util.Random;

import zordz.gfx.NewGLHandler;
import zordz.level.Level;

public class Particle extends Entity {
	protected float dx, dy;
	protected float lifeLength;
	protected float alpha;
	protected float size;
	protected float[] col = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
	private Random r = new Random();
	public Particle(Level l, float x, float y, float size) {
		super(l, x, y);
		this.size = size;
		dx = (r.nextFloat() * 4) - 2;
		dy = (r.nextFloat() * 4) - 2;
		lifeLength = r.nextInt(20) + 5;
	}
	
	public Particle(Level l, float x, float y, float size, float[] col) {
		super(l, x, y);
		this.size = size;
		dx = (r.nextFloat() * 4) - 2;
		dy = (r.nextFloat() * 4) - 2;
		lifeLength = r.nextInt(20) + 5;
		this.col = col;
	}

	public void render() {
		NewGLHandler.setCurrentColor(col, false);
		NewGLHandler.draw2DRect(x, y, size, size, true);
		NewGLHandler.resetColors();
	}

	public void tick() {
		x += dx;
		y += dy;
		lifeLength--;
		if (lifeLength == 0) {
			lvl.remove(this);
		}
	}

}
