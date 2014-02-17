package zordz.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Random;
import java.util.Scanner;

import zordz.Zordz;
import zordz.entity.Entity;
//import zordz.entity.Player;
import zordz.level.tile.Tile;

public class Level {

	public static final int WIDTH = 20;
	public static final int HEIGHT = 15;
	public int width;
	public int height;
	public int tickCount = 0;
	private byte[][] tiles;
	private byte[][] tile_data;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	//private Random rand = new Random();
	//private Player player;
	
	public Level() {
		
	}
	
	public static Level loadLevel(String path) {
		Zordz.zordz.console.write("Loading level " + path);
		int yPos = 0;
		Scanner x = null;
		
		try {
			x = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] Could not find file " + path + "!");
		}
		int wd = 0, ht = 0;
		while (x.hasNextLine()) {
			String row = x.nextLine();
			byte[] tiles = row.getBytes();
			wd = tiles.length;
			ht++;
		}
		x.close();
		try {
			x = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Level level = new Level(wd, ht);
		while (x.hasNextLine()) {
			String row = x.nextLine();
			byte[] tiles = row.getBytes();
			for (int i = 0; i < wd; i++) {
				level.setTileIDAt(i, yPos, tiles[i]);
			}
			yPos++;
		}
		ht = yPos;
		level.setWidth(wd);
		level.setHeight(ht);
		x.close();
		Zordz.zordz.console.write("Done loading level.");
		return level;
	}
	
	public Level(int WIDTH, int HEIGHT) {
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);
		tiles = new byte[WIDTH][WIDTH];
	}

	public byte getTileIDAt(int x, int y) {	
		return tiles[x][y];
	}
	
	public void render() {
		for (int x = (int)(Zordz.zordz.screen.xOff / 32) - 1; x < (int)(Zordz.zordz.screen.xOff / 32) + WIDTH + 1; x++) {
			for (int y = (int)(Zordz.zordz.screen.yOff / 32) - 1; y < (int)(Zordz.zordz.screen.yOff / 32) + HEIGHT + 1; y++) {
				int tempX = x, tempY = y;
				if (x >= width) {
					tempX = width-1;
				} else if (x < 0) {
					tempX = 0;
				} else {
					tempX = x;
				}
				if (y >= height) {
					tempY = height-1;
				} else if (y < 0) {
					tempY = 0;
				} else {
					tempY = y;
				}
				Tile.getByID(tiles[tempX][tempY]).render(this, x * 32, y * 32);
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
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile.getByID(tiles[x][y]).tick(this, x * 32, y * 32);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		tickCount++;
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
