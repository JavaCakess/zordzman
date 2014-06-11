package zordz.entity;

public abstract class Item {

	public int tex_x, tex_y;
	
	public abstract void render();
	public void tick() {};
}
