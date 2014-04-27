package zordz.entity;

import java.util.Random;

import zordz.level.Level;
import cjaf.tools.NewGLHandler;

public class FlameParticle extends Entity {
	protected float dx, dy;
	protected float lifeLength;
	protected float startLifeLength;
	protected float alpha;
	protected float size;
	private Random r = new Random();
	public FlameParticle(Level l, float x, float y, float size) {
		super(l, x, y);
		this.size = size;
		dy = (r.nextFloat() * 1);
		lifeLength = 20;
		startLifeLength = lifeLength;
	}

	public void render() {
		float[] col = new float[]{(lifeLength / startLifeLength) + 0.3f, (lifeLength / startLifeLength) - 0.3f, 0f};
		NewGLHandler.setCurrentColor(col, false);
		NewGLHandler.draw2DRect(x, y, size, size, true);
		NewGLHandler.resetColors();
	}

	public void tick() {
		if (r.nextBoolean()) {
			x += 2;
		} else {
			x -= 2;
		}
		y -= dy;
		lifeLength--;
		if (lifeLength == 0) {
			lvl.remove(this);
		}
	}

}
