package zordz.level;

import java.util.Random;

import zordz.Zordz;
import zordz.level.tile.Tile;

public class Level {

	public static final int WIDTH = 20;
	public static final int HEIGHT = 15;
	private byte[][] tiles = new byte[WIDTH][WIDTH];
	private Random rand = new Random();

	public Level() {
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

	public void render() {
		for (int x = (int)(Zordz.zordz.screen.xOff / 32); x < (int)(Zordz.zordz.screen.xOff / 32) + WIDTH; x++) {
			for (int y = (int)(Zordz.zordz.screen.yOff / 32); y < (int)(Zordz.zordz.screen.yOff / 32) + HEIGHT; y++) {
				Tile.getByID(tiles[x][y]).render(this, x * 32, y * 32);
			}
		}
	}

	public void tick() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Tile.getByID(tiles[x][y]).tick(this, x * 32, y * 32);
			}
		}
	}
}
