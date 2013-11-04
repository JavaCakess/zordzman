package zordz.level;

import java.util.ArrayList;
import java.util.Random;

import zordz.Zordz;
import zordz.entity.Entity;
import zordz.entity.Player;
import zordz.level.tile.Tile;

public class Level {

	public static final int WIDTH = 20;
	public static final int HEIGHT = 15;
	private int width, height;
	private byte[][] tiles;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Random rand = new Random();
	private Player player;
	
	public Level() {
		width = WIDTH;
		height = HEIGHT;
		tiles = new byte[WIDTH][WIDTH];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (rand.nextInt(5) == 0) {
					tiles[x][y] = Tile.FLOWER.getID();
				} else {
					tiles[x][y] = Tile.GRASS.getID();
				}
			}
		}
		tiles[0][4] = Tile.WATER.getID();
	}
	
	public Level(int WIDTH, int HEIGHT) {
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);
		tiles = new byte[WIDTH][WIDTH];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (rand.nextInt(5) == 0) {
					tiles[x][y] = Tile.FLOWER.getID();
				} else {
					tiles[x][y] = Tile.GRASS.getID();
				}
			}
		}
	}

	public byte getTileIDAt(int x, int y) {	
		return tiles[x][y];
	}
	
	public void render() {
		for (int x = (int)(Zordz.zordz.screen.xOff / 32); x < (int)(Zordz.zordz.screen.xOff / 32) + WIDTH; x++) {
			for (int y = (int)(Zordz.zordz.screen.yOff / 32); y < (int)(Zordz.zordz.screen.yOff / 32) + HEIGHT; y++) {
				Tile.getByID(tiles[x][y]).render(this, x * 32, y * 32);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render();
		}
	}
	
	public void add(Entity e) {
		entities.add(e);
	}
	
	public void remove(Entity e) {
		if (entities.contains(e)) entities.remove(e);
	}

	public void tick() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Tile.getByID(tiles[x][y]).tick(this, x * 32, y * 32);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTileIDAt(int x, int y, byte id) {
		this.tiles[x][y] = id;
	}
}
