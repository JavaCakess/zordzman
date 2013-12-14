package zordz.level.tile;

import zordz.level.Level;

public abstract class Tile {

	int id, ssx, ssy, col;
	boolean solid = false;
	
	public static Tile[] tiles = new Tile[256];
	public static Tile GRASS = new GrassTile(0, 0, 0);
	public static Tile FLOWER = new FlowerTile(1, 1, 0);
	public static Tile WATER = new WaterTile(2);
	public static Tile SAND = new SandTile(3, 5, 0);
	public static Tile SAND_ROCK = new SandRockTile(4, 6, 0);
	
	public Tile(int id, int ssx, int ssy, int col, boolean solid) {
		this.ssx = ssx;
		this.ssy = ssy;
		this.id = id;
		this.solid = solid;
		tiles[id] = this;
	}
	
	public abstract void render(Level level, float x, float y);
	public abstract void tick(Level level, float x, float y);

	public byte getID() {
		return (byte)id;
	}

	public static Tile getByID(byte b) {
		for (Tile t : tiles) {
			if (t.id == b) {
				return t;
			}
		}
		return null;
	}

	public int getCol() {
		return col;
	}
}
